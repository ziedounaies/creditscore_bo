import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import {Row, Col, Form, Input, Button, Card, Divider, Typography, Select} from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getMemberUsers } from 'app/modules/dashboard/member-user/member-user.reducer';
import { IBanks } from 'app/shared/model/banks.model';
import { getEntities as getBanks } from 'app/entities/banks/banks.reducer';
import { IAgencies } from 'app/shared/model/agencies.model';
import { getEntities as getAgencies } from 'app/entities/agencies/agencies.reducer';
import { getEntity, updateEntity, createEntity, reset } from './credit-rapport.reducer';
import {
  CheckCircleOutlined,
  ClockCircleOutlined,
  CreditCardOutlined,
  HourglassOutlined,
  MessageOutlined, TrophyOutlined
} from "@ant-design/icons";
import dayjs from "dayjs";

const { Title,Text } = Typography;

const CreditRapportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const creditRapportEntity = useAppSelector(state => state.creditRapport.entity);
  const loading = useAppSelector(state => state.creditRapport.loading);
  const updating = useAppSelector(state => state.creditRapport.updating);
  const updateSuccess = useAppSelector(state => state.creditRapport.updateSuccess);
  const [form] = Form.useForm();


  useEffect(() => {
    if (updateSuccess) {
      // Redirect to "/dashboard/bank" after successful update
      navigate('/dashboard/credit_rapport');
    }
  }, [updateSuccess]);
  const handleClose = () => {
    navigate('/dashboard/credit_rapport' + location.search);
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
      ...creditRapportEntity,
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
        ...creditRapportEntity,
        createdAt: convertDateTimeFromServer(creditRapportEntity.createdAt),
        memberUser: creditRapportEntity?.memberUser?.id,
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        createdAt: displayDefaultDateTime(),
      })
      : form.setFieldsValue({
        ...creditRapportEntity,
        createdAt: convertDateTimeFromServer(creditRapportEntity.createdAt),
        memberUser: creditRapportEntity?.memberUser?.id,
      });
  }, [creditRapportEntity]);

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


            <Title level={2} style={{marginBottom:0}}>{isNew ? `Créer un rapport credit`: `Fiche rapport credit`}</Title>


            <Text type="secondary">
              Rapport de Crédit - Consultez Votre Historique Financier
            </Text>

          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/credit_rapport">
                <Button className="me-2" style={{ borderRadius: 10, backgroundColor: 'white', color: '#333' }}>
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Retour</span>
                </Button>
              </Link>
              <Button
                type="primary"
                style={{ borderRadius: 10, backgroundColor: '#FF3029' }}
                htmlType="submit"
                form="creditRapportForm"
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
            <Form
              layout="vertical"
              name="creditRapportForm"
              onFinish={saveEntity}
              initialValues={defaultValues()}
              // Ensure correct usage of spread operator here
              {...{ onFinish: saveEntity, initialValues: defaultValues() }}
              form={form}
            >

              <Form.Item
label="Age compte"
                name="accountAge"
rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input
                  prefix={<HourglassOutlined     className="site-form-item-icon" />}
                  placeholder="Age compte" />
              </Form.Item>



              <Form.Item
label="Limite credit"
                name="creditLimit"
rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input
                  prefix={<CreditCardOutlined      className="site-form-item-icon" />}
                  placeholder="Limite credit" />
              </Form.Item>



              <Form.Item
label="Credit score"
                name="creditScore"
rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input
                  prefix={<TrophyOutlined      className="site-form-item-icon" />}
                  placeholder="Credit score" />
              </Form.Item>




              <Form.Item
label="Demandes de renseignements"
                name="inquiriesAndRequests"
rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input
                  prefix={<MessageOutlined      className="site-form-item-icon" />}
                  placeholder="demandes de renseignements" />
              </Form.Item>


              <Form.Item
                id="address-memberUser"
                label="user"
                name="memberUser"
                rules={[{ required:true,message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select

                >
                  <option value="" key="" />
                  {memberUsers
                    ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.userName}
                      </option>
                    ))
                    : null}      </Select>
              </Form.Item>






            </Form>
          </Col>
          <Divider type="vertical" style={{ height: 525 }} />
          <Col span={11} >


            {/* Additional content for the right half of the page */}
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default CreditRapportUpdate;
