import React, { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Card, Button, Form, Input, Select, Typography, Row, Col, Divider } from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { DollarOutlined, CheckCircleOutlined, NumberOutlined } from '@ant-design/icons';
import { useDispatch, useSelector } from 'react-redux';

import { reset } from './invoice.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import { useAppDispatch, useAppSelector } from "app/config/store";
import { createEntity, getEntity, updateEntity } from "app/modules/dashboard/invoice/invoice.reducer";
import { getEntities as getMemberUsers } from "app/modules/dashboard/member-user/member-user.reducer";
import { getEntities as getCreditRapports } from "app/modules/dashboard/credit-rapport/credit-rapport.reducer";

const { Title, Text } = Typography;

const InvoiceUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const creditRapports = useAppSelector(state => state.creditRapport.entities);
  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  const loading = useAppSelector(state => state.invoice.loading);
  const updating = useAppSelector(state => state.invoice.updating);
  const updateSuccess = useAppSelector(state => state.invoice.updateSuccess);
  const statusTypeValues = Object.keys(StatusType);
  const [form] = Form.useForm();

  const handleClose = () => {
    navigate('/dashboard/invoice' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
    dispatch(getCreditRapports({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);


  useEffect(() => {
    if (updateSuccess) {
      // Redirect to "/dashboard/bank" after successful update
      navigate('/dashboard/invoice');
    }
  }, [updateSuccess]);


  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...invoiceEntity,
      ...values,
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
      creditRapport: creditRapports.find(it => it.id.toString() === values.creditRapport.toString()),
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
        ...invoiceEntity,
        createdAt: convertDateTimeFromServer(invoiceEntity.createdAt),
        memberUser: invoiceEntity?.memberUser?.id,
        creditRapport: invoiceEntity?.creditRapport?.id,
      };

  const CustomPlaceholderStatus = (
    <span>
      <CheckCircleOutlined style={{ marginRight: 8, color: 'black' }} />
      Sélectionnez le statut
    </span>
  );

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        createdAt: displayDefaultDateTime(),
      })
      : form.setFieldsValue({
        status: 'PENDING',
        ...invoiceEntity,
        createdAt: convertDateTimeFromServer(invoiceEntity.createdAt),
        memberUser: invoiceEntity?.memberUser?.id,
        creditRapport: invoiceEntity?.creditRapport?.id,
      });
  }, [invoiceEntity]);


  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%', height: 690 }}>

        <div style={{ marginBottom: 15 }}>
          <Title level={2} style={{ marginBottom: 0 }}>{isNew ? 'Créer une facture' : 'Fiche facture'}</Title>
          <Text type="secondary">Gérez et Consultez Vos Factures</Text>
        </div>
        <Form onFinish={saveEntity} layout="vertical" name="invoiceForm" initialValues={defaultValues()} form={form}>
          <div style={{ display: 'flex',flexDirection:'row',justifyContent:'right',marginTop:-70 }}>
            <Form.Item>

              <Button  style={{marginRight:10}} type="default" onClick={handleClose} >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp; Retour
              </Button>
              <Button  type="primary" htmlType="submit">
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </Form.Item>
          </div>
<Divider/>
          <Row gutter={[16, 16]}>
            <Col span={12}>
              <Form.Item label="Numéro de facture" name="invoiceNumber" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<NumberOutlined />} placeholder="Numéro de facture" />
              </Form.Item>

              <Form.Item label="Montant" name="amount" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<DollarOutlined />} placeholder="Montant" />
              </Form.Item>

              <Form.Item label="Statut" name="status" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Select placeholder={CustomPlaceholderStatus} style={{ width: 650 }}>
                  {statusTypeValues.map(statusType => (
                    <Select.Option key={statusType} value={statusType}>
                      {statusType.toLowerCase()}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>

              <Form.Item label="User" name="memberUser" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Select style={{ width: 650 }}>
                  <option value="" key="" />
                  {memberUsers
                    ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.userName}
                      </option>
                    ))
                    : null}
                </Select>
              </Form.Item>

              <Form.Item label="Rapport de crédit" name="creditRapport" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Select style={{ width: 650 }}>
                  {creditRapports.map(report => (
                    <Select.Option key={report.id} value={report.id}>
                      {report.memberUser?.lastName?.toUpperCase()} {report.memberUser?.firstName}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>

            </Col>
            <Col span={12}>
              <Divider type="vertical" style={{ height: 525 }} />


            </Col>
          </Row>
        </Form>
      </Card>
    </div>
  );
};

export default InvoiceUpdate;
