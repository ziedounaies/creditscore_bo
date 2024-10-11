import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, Form, Input, Card, Divider, Typography, Select } from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';
import {
  BarcodeOutlined,
  CheckCircleOutlined,
  EnvironmentOutlined,
  GlobalOutlined,
} from "@ant-design/icons";
import { getEntities as getMemberUsers } from 'app/entities/member-user/member-user.reducer';
import { getEntities as getBanks } from 'app/entities/banks/banks.reducer';
import { getEntities as getAgencies } from 'app/entities/agencies/agencies.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from "app/shared/util/date-utils";
import moment from "moment/moment";

const { Title, Text } = Typography;

const AddressUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const updating = useAppSelector(state => state.address.updating);
  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const banks = useAppSelector(state => state.banks.entities);
  const agencies = useAppSelector(state => state.agencies.entities);
  const addressEntity = useAppSelector(state => state.address.entity);
  const loading = useAppSelector(state => state.address.loading);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);

  const [form] = Form.useForm();

  const handleClose = () => {
    navigate('/address' + location.search);
  };

  useEffect(() => {
    if (updateSuccess) {
      // Redirect to "/dashboard/bank" after successful update
      navigate('/dashboard/address');
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
    dispatch(getBanks({}));
    dispatch(getAgencies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = async values => {
    try {
      // Convert ID to number if it exists and is not already a number
      if (values.id !== undefined && typeof values.id !== 'number') {
        values.id = Number(values.id);
      }

      // Convert createdAt date to server format
      values.createdAt = convertDateTimeToServer(values.createdAt);

      // Construct the entity object
      const entity = {
        ...addressEntity,
        ...values,
        memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
        banks: banks.find(it => it.id.toString() === values.banks.toString()),
        agencies: agencies.find(it => it.id.toString() === values.agencies.toString()),
      };

      // Dispatch the appropriate action based on whether it's a new entity or an update
      if (isNew) {
        await dispatch(createEntity(entity));
      } else {
        await dispatch(updateEntity(entity));
      }

      // Navigate to the desired URL upon successful save
      navigate('/dashboard/address');
    } catch (error) {
      console.error('Error saving entity:', error);
      // Handle errors if necessary
    }
  };

  const defaultValues = () =>
    isNew
      ? {
        createdAt: displayDefaultDateTime(),
      }
      : {
        ...addressEntity,
        createdAt: convertDateTimeFromServer(addressEntity.createdAt),
        memberUser: addressEntity?.memberUser?.id,
        banks: addressEntity?.banks?.id,
        agencies: addressEntity?.agencies?.id,
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        createdAt: displayDefaultDateTime(),
      })
      : form.setFieldsValue({
        ...addressEntity,
        createdAt: convertDateTimeFromServer(addressEntity.createdAt),
        memberUser: addressEntity?.memberUser?.id,
        banks: addressEntity?.banks?.id,
        agencies: addressEntity?.agencies?.id,
      });
  }, [addressEntity]);

  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%', height: 690 }}>
        <Row justify="space-between" style={{ marginBottom: 15 }}>
          <Col>
            <Title level={2} style={{ marginBottom: 0 }}>
              {isNew ? `Créer une adresse` : `Fiche adresse`}
            </Title>
            <Text type="secondary">Adresses des Banques - Localisez les Succursales</Text>
          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/address">
                <Button
                  className="me-2"
                  style={{ borderRadius: 10, backgroundColor: 'white', color: '#333' }}
                >
                  <FontAwesomeIcon style={{marginRight:8}} icon="arrow-left" />
                  <span className="d-none d-md-inline">Retour</span>
                </Button>
              </Link>
              <Button
                type="primary"
                id="save-entity"
                data-cy="entityCreateSaveButton"
                disabled={updating}
                style={{ borderRadius: 10, backgroundColor: '#FF3029' }}
                htmlType="submit"
                form="addressForm"
              >
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </div>
          </Col>
        </Row>
        <Divider />
        <Row>
          <Col span={11}>
            <Form  layout="vertical"
                   name="addressForm"
                   onFinish={saveEntity}
                   initialValues={defaultValues()}
                   form={form}
              // Ensure correct usage of spread operator here
                   {...{ onFinish: saveEntity, initialValues: defaultValues() }}>

              <Form.Item label="Rue" id="address-street" name="street" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<EnvironmentOutlined className="site-form-item-icon" />} placeholder="Rue" />
              </Form.Item>

              <Form.Item label="Ville" id="address-city" name="city" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<GlobalOutlined className="site-form-item-icon" />} placeholder="Ville" />
              </Form.Item>

              <Form.Item label="Code postal" name="zipCode" id="address-zipCode" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<BarcodeOutlined className="site-form-item-icon" />} placeholder="Code postal" />
              </Form.Item>

              <Form.Item id="address-memberUser" label="Client" name="memberUser" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Select>
                  <option value="" key="" />
                  {memberUsers ? memberUsers.map(user => (
                    <option value={user.id} key={user.id}>{user.firstName}</option>
                  )) : null}
                </Select>
              </Form.Item>

              <Form.Item id="address-banks" label="Banque" name="banks" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Select>
                  <option value="" key="" />
                  {banks ? banks.map(bank => (
                    <option value={bank.id} key={bank.id}>{bank.name}</option>
                  )) : null}
                </Select>
              </Form.Item>

              <Form.Item id="address-agencies" label="Agence" name="agencies" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Select>
                  <option value="" key="" />
                  {agencies ? agencies.map(agency => (
                    <option value={agency.id} key={agency.id}>{agency.name}</option>
                  )) : null}
                </Select>
              </Form.Item>

            </Form>
          </Col>

          <Col span={11}>


            <Divider type="vertical" style={{ height: 525 }} />

          </Col>






        </Row>
      </Card>
    </div>
  );
};

export default AddressUpdate;
