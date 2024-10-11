import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, Form, Input, Card, Divider, Typography, Select } from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, updateEntity, createEntity, reset } from './claim.reducer';
import {CheckCircleOutlined, PhoneOutlined, UserOutlined} from "@ant-design/icons";
import {getEntities as getMemberUsers} from "app/entities/member-user/member-user.reducer";
import {getEntities as getBanks} from "app/entities/banks/banks.reducer";
import {getEntities as getAgencies} from "app/entities/agencies/agencies.reducer";
import {StatusType} from "app/shared/model/enumerations/status-type.model";
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from "app/shared/util/date-utils";
import moment from "moment/moment";

const { Title,Text } = Typography;

const ClaimUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const claimEntity = useAppSelector(state => state.claim.entity);
  const loading = useAppSelector(state => state.claim.loading);
  const updating = useAppSelector(state => state.claim.updating);
  const updateSuccess = useAppSelector(state => state.claim.updateSuccess);
  const statusTypeValues = Object.keys(StatusType);
  const [form] = Form.useForm();

  const handleClose = () => {
    navigate('/dashboard/claim' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...claimEntity,
      ...values,
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
        createdAt: displayDefaultDateTime(),
      }
      : {
        status: 'PENDING',
        ...claimEntity,
        createdAt: convertDateTimeFromServer(claimEntity.createdAt),
        memberUser: claimEntity?.memberUser?.id,
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        createdAt: displayDefaultDateTime(),
      })
      : form.setFieldsValue({
        status: 'PENDING',
        ...claimEntity,
        createdAt: convertDateTimeFromServer(claimEntity.createdAt),
        memberUser: claimEntity?.memberUser?.id,
      });
  }, [claimEntity]);
  const onFinish = values => {
    saveEntity(values);
  };
  const handleChange = (value: string) => {
    console.log(`selected ${value}`);
  };
  const CustomPlaceholder = (
    <span>
   <CheckCircleOutlined style={{ marginRight: 8, color: 'black' }} />
    Sélectionnez type de statut
  </span>
  );

  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%' ,height:690}}>
        <Row justify="space-between" style={{ marginBottom: 15 }}>
          <Col>



            <Title level={2} style={{marginBottom:0}}>{isNew ? `Créer une reclamation`: `Fiche reclamation`}</Title>


            <Text type="secondary">
              Réclamations - Faites Valoir Vos Droits
            </Text>



          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/claim">
                <Button className="me-2" style={{ borderRadius: 10, backgroundColor: 'white', color: '#333' }}>
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Retour</span>
                </Button>
              </Link>

              <Button type="primary" style={{ borderRadius: 10, backgroundColor: '#FF3029' }} htmlType="submit" form="claimForm">
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </div>
          </Col>
        </Row>
        <Divider />
        <Row>
          <Col span={11}>
            <Form layout="vertical" name="claimForm" onFinish={onFinish} initialValues={defaultValues()} form={form}>





              <Form.Item
                label="Statut"
                name="status"
                rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select
                  placeholder={CustomPlaceholder}
                  style={{ width: 600 }}
                  onChange={handleChange}
                >
                  <Select.Option value="PENDING">En attente</Select.Option>
                  <Select.Option value="IN_PROGRESS, ">En cours</Select.Option>
                  <Select.Option value="DONE">Terminé</Select.Option>
                  <Select.Option value="REJECTED">Rejeté</Select.Option>
                </Select>
              </Form.Item>






              <Form.Item label="Titre" name="title"    rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Emetteur" />
              </Form.Item>

              <Form.Item label="Message" name="message"    rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input.TextArea

                  rows={4} placeholder="Message" />
              </Form.Item>


              <Form.Item
                id="claim-memberUser"
                label="Client"
                name="memberUser"
                rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select
                  //  placeholder={CustomPlaceholder}
                  style={{ width: 600 }}
                 // onChange={handleChange}
                >
                  <option value="" key="" />
                  {memberUsers ? memberUsers.map(user => (
                    <option value={user.id} key={user.id}>{user.lastName?.toUpperCase()} {user.firstName}</option>
                  )) : null}

                </Select>
              </Form.Item>

            </Form>
          </Col>
          <Divider type="vertical" style={{ height: 525 }} />
          <Col span={12} >
            {/* Additional content for the right half of the page */}
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default ClaimUpdate;
