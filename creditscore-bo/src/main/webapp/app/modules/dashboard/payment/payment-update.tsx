import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import {Button, Row, Col, Card, Form, Input, Select, DatePicker, Spin, Typography, Divider} from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import moment from 'moment';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getMemberUsers } from 'app/entities/member-user/member-user.reducer';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { getEntities as getInvoices } from 'app/entities/invoice/invoice.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payment.reducer';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import {BankOutlined, BarcodeOutlined, DollarCircleOutlined, DollarOutlined, UserOutlined} from "@ant-design/icons";

const { Option } = Select;

const VerticalDivider = () => (
  <div style={{ borderLeft: '1px solid #e8e8e8', height: '100%' }} />
);

export const PaymentUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const { Title, Text } = Typography;
  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const products = useAppSelector(state => state.product.entities);
  const invoices = useAppSelector(state => state.invoice.entities);
  const paymentEntity = useAppSelector(state => state.payment.entity);
  const loading = useAppSelector(state => state.payment.loading);
  const updating = useAppSelector(state => state.payment.updating);
  const updateSuccess = useAppSelector(state => state.payment.updateSuccess);
  const paymentTypeValues = Object.keys(PaymentType);
  const statusTypeValues = Object.keys(StatusType);

  const [form] = Form.useForm();

  const handleClose = () => {
    navigate('/payment' + location.search);
  };

  useEffect(() => {
    if (updateSuccess) {
      // Redirect to "/dashboard/bank" after successful update
      navigate('/dashboard/payment');
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
    dispatch(getProducts({}));
    dispatch(getInvoices({}));
  }, []);

  useEffect(() => {
    if (!isNew && paymentEntity) {
      form.setFieldsValue({
        ...paymentEntity,
        checkDate: paymentEntity.checkDate ? moment(paymentEntity.checkDate) : null,
        dateOfSignature: paymentEntity.dateOfSignature ? moment(paymentEntity.dateOfSignature) : null,
        expectedPaymentDate: paymentEntity.expectedPaymentDate ? moment(paymentEntity.expectedPaymentDate) : null,
        datePaymentMade: paymentEntity.datePaymentMade ? moment(paymentEntity.datePaymentMade) : null,
        createdAt: paymentEntity.createdAt ? moment(paymentEntity.createdAt) : null,
        memberUser: paymentEntity.memberUser?.id,
        products: paymentEntity.products?.map(e => e.id),
        invoice: paymentEntity.invoice?.id,
      });
    }
  }, [paymentEntity]);



  const saveEntity = values => {
    const entity = {
      ...paymentEntity,
      ...values,
      checkDate: values.checkDate ? values.checkDate.toISOString() : null,
      dateOfSignature: values.dateOfSignature ? values.dateOfSignature.toISOString() : null,
      expectedPaymentDate: values.expectedPaymentDate ? values.expectedPaymentDate.toISOString() : null,
      datePaymentMade: values.datePaymentMade ? values.datePaymentMade.toISOString() : null,
      createdAt: values.createdAt ? values.createdAt.toISOString() : null,
      products: mapIdList(values.products),
      memberUser: memberUsers.find(it => it.id === values.memberUser),
      invoice: invoices.find(it => it.id === values.invoice),
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
        checkDate: moment(displayDefaultDateTime()),
        dateOfSignature: moment(displayDefaultDateTime()),
        expectedPaymentDate: moment(displayDefaultDateTime()),
        datePaymentMade: moment(displayDefaultDateTime()),
        createdAt: moment(displayDefaultDateTime()),
      }
      : {
        paymentMethod: 'CHECK',
        status: 'PENDING',
        ...paymentEntity,
        checkDate: paymentEntity.checkDate ? moment(paymentEntity.checkDate) : null,
        dateOfSignature: paymentEntity.dateOfSignature ? moment(paymentEntity.dateOfSignature) : null,
        expectedPaymentDate: paymentEntity.expectedPaymentDate ? moment(paymentEntity.expectedPaymentDate) : null,
        datePaymentMade: paymentEntity.datePaymentMade ? moment(paymentEntity.datePaymentMade) : null,
        createdAt: paymentEntity.createdAt ? moment(paymentEntity.createdAt) : null,
        memberUser: paymentEntity?.memberUser?.id,
        products: paymentEntity?.products?.map(e => e.id),
        invoice: paymentEntity?.invoice?.id,
      };

  const CustomPlaceholderTypeAccount = (
    <span>
      <BankOutlined style={{ marginRight: 8, color: 'black' }} />
      Sélectionnez le status
    </span>
  );


  return (
    <Row justify="center">
      <Col md="18">
        <Card style={{ width: 1365 }}>
          <Form form={form} initialValues={defaultValues()} onFinish={saveEntity} layout="vertical">
            <div style={{display:"flex",flexDirection:'row',justifyContent:'space-between'}}>
            <div>
          <Title style={{ marginBottom: 0 }} level={2}>
            {isNew ? 'Créer un paiement' : 'Fiche paiement'}
          </Title>
          <Text type="secondary">Paiements - Gérez Vos Transactions Financières</Text>
            </div>
            <Form.Item>
              <Link to="/dashboard/payment">
                <Button style={{ marginRight: 7 }}>
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp; Retour
                </Button></Link>

              <Button type="primary" htmlType="submit" loading={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
              &nbsp;
            </Form.Item>
            </div>
          <Divider style={{width:550}}/>

            <Row gutter={16}>
              <Col span={11}>


                <Form.Item
                  name="checkNumber"
                  label="Numéro de chéque"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Input prefix={<BarcodeOutlined />}  placeholder="Numero cheque"/>
                </Form.Item>
                <Form.Item
                  name="checkIssuer"
                  label="Émetteur"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Input prefix={<UserOutlined />} placeholder="Émetteur"/>
                </Form.Item>
                <Form.Item
                  name="accountNumber"
                  label="Numéro compte"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Input  prefix={<BankOutlined />} placeholder="Numéro compte" />
                </Form.Item>
                <Form.Item
                  name="checkDate"
                  label="Date chéque"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <DatePicker style={{ width: '100%' }} format="YYYY-MM-DD" placeholder="Sélectionnez une date" />
                </Form.Item>
                <Form.Item
                  name="recipient"
                  label="Destinataire"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Input  prefix={<UserOutlined />} placeholder="Destinataire"  />
                </Form.Item>
                <Form.Item
                  name="dateOfSignature"
                  label="Date de Signature"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <DatePicker style={{ width: '100%' }} format="YYYY-MM-DD" placeholder="Sélectionnez une date" />
                </Form.Item>
                <Form.Item

                  name="paymentMethod"
                  label="Méthode de paiement"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' ,}]}
                >

                  <Select placeholder="Methode de paiement">


                    <Select.Option value={"CHECK"} key={"CHECK"}>
                      Chéque
                    </Select.Option>
                    <Select.Option value={"CASH"} key={"CASH"}>
                      Espèce

                    </Select.Option>
                    <Select.Option value={"DEBIT"} key={"DEBIT"}>

                      Débit
                    </Select.Option>

                    {/*
                    {paymentTypeValues.map(paymentType => (
                      <Option value={paymentType} key={paymentType}>
                        {paymentType}
                      </Option>
                    ))}*/}
                  </Select>
                </Form.Item>
              </Col>
              <Col span={2}>
                <VerticalDivider />
              </Col>
              <Col style={{marginLeft:-80}} span={11}>
                <Form.Item name="amount" label="Montant" rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}>
                  <Input  prefix={<DollarOutlined />} placeholder="Montant" />
                </Form.Item>
                <Form.Item
                  name="expectedPaymentDate"
                  label="Date de paiement expecté"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <DatePicker style={{ width: 590 }} format="YYYY-MM-DD" placeholder="Sélectionnez une date" />
                </Form.Item>
                <Form.Item
                  name="datePaymentMade"
                  label="Date de paiement effectué"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <DatePicker style={{ width: 590 }} format="YYYY-MM-DD" placeholder="Sélectionnez une date" />
                </Form.Item>
                <Form.Item
                  name="status"
                  label="Statut"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >


                  <Select placeholder={CustomPlaceholderTypeAccount} style={{ width: 590 }}>
                    <Select.Option value={"PENDING"} key={"PENDING"}>
                      En attente
                    </Select.Option>
                    <Select.Option value={"IN_PROGRESS"} key={"IN_PROGRESS"}>
                      En cours

                    </Select.Option>
                    <Select.Option value={"DONE"} key={"DONE"}>

                      Payé
                    </Select.Option>

                    <Select.Option value={"REJECTED"} key={"REJECTED"}>

                      Impayé
                    </Select.Option>


                  </Select>



                </Form.Item>
                <Form.Item
                  name="currency"
                  label="Devise"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Input prefix={<DollarCircleOutlined />} placeholder="Devise" />
                </Form.Item>
                <Form.Item
                  name="memberUser"
                  label="Member User"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Select>
                    <Option value="" key="0">
                      Select Member User
                    </Option>
                    {memberUsers
                      ? memberUsers.map(otherEntity => (
                        <Option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.firstName}
                        </Option>
                      ))
                      : null}
                  </Select>
                </Form.Item>
                <Form.Item
                  name="products"
                  label="Produit"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Select mode="multiple">
                    {products
                      ? products.map(otherEntity => (
                        <Option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </Option>
                      ))
                      : null}
                  </Select>
                </Form.Item>
                <Form.Item
                  name="invoice"
                  label="Facture"
                  rules={[{ required: true, message: 'Veuillez renseigner ce champ!' }]}
                >
                  <Select>
                    <Option value="" key="0">
                      Select Invoice
                    </Option>
                    {invoices
                      ? invoices.map(otherEntity => (
                        <Option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.invoiceNumber}
                        </Option>
                      ))
                      : null}
                  </Select>
                </Form.Item>
              </Col>
            </Row>


          </Form>
        </Card>
      </Col>
    </Row>
  );
};

export default PaymentUpdate;
