import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import {Row, Col, Form, Input, Button, Card, Divider, Typography, Select} from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, updateEntity, createEntity, reset } from './contact.reducer';
import {MessageOutlined, PhoneOutlined} from "@ant-design/icons";
import {getEntities as getMemberUsers} from "app/entities/member-user/member-user.reducer";
import {getEntities as getBanks} from "app/entities/banks/banks.reducer";
import {getEntities as getAgencies} from "app/entities/agencies/agencies.reducer";
import {TypeContact} from "app/shared/model/enumerations/type-contact.model";

const { Title,Text } = Typography;

const ContactUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const banks = useAppSelector(state => state.banks.entities);
  const agencies = useAppSelector(state => state.agencies.entities);
  const contactEntity = useAppSelector(state => state.contact.entity);
  const loading = useAppSelector(state => state.contact.loading);
  const updating = useAppSelector(state => state.contact.updating);
  const updateSuccess = useAppSelector(state => state.contact.updateSuccess);
  const typeContactValues = Object.keys(TypeContact);

  const [form] = Form.useForm();

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      navigate('/dashboard/contact');
    }
  }, [updateSuccess]);



  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...contactEntity,
      ...values,
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
      banks: banks.find(it => it.id.toString() === values.banks.toString()),
      agencies: agencies.find(it => it.id.toString() === values.agencies.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

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
  const onFinish = values => {
    const formattedValues = {
      ...values,
      // Any formatting needed for the form values
    };
    if (isNew) {
      dispatch(createEntity(formattedValues));
    } else {
      dispatch(updateEntity({ id, ...formattedValues }));
    }
  };


  const handleChange = (value: string) => {
    console.log(`selected ${value}`);
  };
  const CustomPlaceholder = (
    <span>
   <PhoneOutlined style={{ marginRight: 8, color: 'black' }} />
    Sélectionnez type de contact
  </span>
  );



  const defaultValues = () =>
    isNew
      ? {
        createdAt: displayDefaultDateTime(),
      }
      : {
        type: 'PHONE_NUMBER',
        ...contactEntity,
        createdAt: convertDateTimeFromServer(contactEntity.createdAt),
        memberUser: contactEntity?.memberUser?.id,
        banks: contactEntity?.banks?.id,
        agencies: contactEntity?.agencies?.id,
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        createdAt: displayDefaultDateTime(),
      })
      : form.setFieldsValue({
        type: 'PHONE_NUMBER',
        ...contactEntity,
        createdAt: convertDateTimeFromServer(contactEntity.createdAt),
        memberUser: contactEntity?.memberUser?.id,
        banks: contactEntity?.banks?.id,
        agencies: contactEntity?.agencies?.id,
      });
  }, [contactEntity]);


  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%',height:690 }}>
        <Row justify="space-between" style={{ marginBottom: 15 }}>
          <Col>

            <Title level={2} style={{marginBottom:0}}>{isNew ? `Créer un contact`: `Fiche contact`}</Title>


            <Text type="secondary">
              Nous Contacter - Restons en Contact
            </Text>



          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/contact">
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
                form="contactForm"
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
              name="contactForm"
              onFinish={saveEntity}
              initialValues={defaultValues()}
              // Ensure correct usage of spread operator here
              {...{ onFinish: saveEntity, initialValues: defaultValues() }}
              form={form}
            >


              <Form.Item
                label="type contact"
                name="type"
                id="contact-type"
                rules={[{ required:true, message: 'veuillez renseigner ce champ !' }]}
              >
                <Select
                  placeholder={CustomPlaceholder}
                  style={{ width: 600 }}
                  onChange={handleChange}
                >
                  {typeContactValues.map(typeContact => (
                    <option value={typeContact} key={typeContact}>
                      {typeContact.toLowerCase()}
                    </option>
                  ))}
                </Select>
              </Form.Item>



              <Form.Item
label="Valeur"
                name="value"
rules={[{ required:true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input
                  prefix={<PhoneOutlined       className="site-form-item-icon" />}
                  placeholder="valeur" />
              </Form.Item>

              <Form.Item
                id="address-memberUser"
                label="Client"
                name="memberUser"
                rules={[{ required:true,message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select

                >
                  <option value="" key="" />
                  {memberUsers
                    ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.lastName?.toUpperCase()} {otherEntity.userName}
                      </option>
                    ))
                    : null}      </Select>
              </Form.Item>





              <Form.Item
                id="address-banks"
                label="banque"
                name="banks"
                rules={[{ required:true,message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select

                >
                  <option value="" key=""/>
                  {banks
                    ? banks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                    : null}      </Select>
              </Form.Item>
              <Form.Item
                id="address-agencies" name="agencies" data-cy="agencies" label="Agencies"


                rules={[{ required:true,message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select

                >
                  <option value="" key=""/>
                  {agencies
                    ? agencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                    : null}

                </Select>
              </Form.Item>


              {/* Add more form items here for other contact fields */}

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

export default ContactUpdate;
