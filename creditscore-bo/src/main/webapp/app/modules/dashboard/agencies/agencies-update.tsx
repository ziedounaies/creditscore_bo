import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import {
  Row,
  Col,
  Form,
  Input,
  Button,
  Card,
  Divider,
  Typography,
  Select,
  Checkbox,
  type CheckboxProps,
  DatePicker
} from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { convertDateTimeFromServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, updateEntity, createEntity, reset } from './agencies.reducer';
import { getEntities as getBanks } from 'app/entities/banks/banks.reducer';

import {
  BankOutlined,
  BarcodeOutlined,
  CalendarOutlined,
  CheckCircleOutlined,
  EnvironmentOutlined, GlobalOutlined, PhoneOutlined
} from "@ant-design/icons";
import moment from "moment";
import dayjs from "dayjs";

const { Title,Text } = Typography;

const AgenciesUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const [form] = Form.useForm();

  const agenciesEntity = useAppSelector(state => state.agencies.entity);
  const loading = useAppSelector(state => state.agencies.loading);
  const updating = useAppSelector(state => state.agencies.updating);
  const updateSuccess = useAppSelector(state => state.agencies.updateSuccess);
  const banks = useAppSelector(state => state.banks.entities);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBanks({}));
  }, []);

  useEffect(() => {
   form.setFieldsValue({
     ...agenciesEntity,
     banks: agenciesEntity?.banks?.id
   })
     }, [agenciesEntity]);


  useEffect(() => {
    if (updateSuccess) {
      navigate('/dashboard/agencies');
    }
  }, [updateSuccess]);

  const onFinish = values => {
    const formattedValues = {
      ...values,
      datefounded: values.datefounded ? dayjs(values.datefounded): null,
      banks: banks.find(it => it.id.toString() === values.banks.toString())
      // Any other formatting needed for form values
    };
    if (isNew) {
      dispatch(createEntity(formattedValues));
    } else {
      dispatch(updateEntity({ id, ...formattedValues }));
    }
  };



  const defaultValues = () =>
    isNew
      ? {
        // Set default values for new agencies here if needed
      }
      : {
        ...agenciesEntity,
        datefounded: agenciesEntity.datefounded ? dayjs(agenciesEntity.datefounded) : null,
        // Any necessary formatting for existing agencies data
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({

      })
      : form.setFieldsValue({
        ...agenciesEntity,
        datefounded: agenciesEntity.datefounded ? dayjs(agenciesEntity.datefounded) : null,
        banks: agenciesEntity?.banks?.id,
      });
  }, [agenciesEntity]);
  const handleChange = (value: string) => {
    console.log(`selected ${value}`);
  };

  const CustomPlaceholder = (
    <span>
   <PhoneOutlined style={{ marginRight: 8, color: 'black' }} />
    Sélectionnez type de contact
  </span>
  );
  const onChange: CheckboxProps['onChange'] = (e) => {
    console.log(`checked = ${e.target.checked}`);
  };
  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%',height:690 }}>
        <Row justify="space-between" style={{ marginBottom: 15 }}>
          <Col>




            <Title level={2} style={{marginBottom:0}}>{isNew ? `Créer une agence`: `Fiche agence`}</Title>


            <Text type="secondary">
              Agences Bancaires - Découvrez Nos Points de Service
            </Text>
          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/agencies">
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
                form="agenciesForm"
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
              form={form}
              layout="vertical"
              name="agenciesForm"
              onFinish={onFinish}
            >
              <Form.Item
                name="name"
                label="Nom"
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Input
                  prefix={<BankOutlined className="site-form-item-icon"/>}

                  placeholder="Nom agence"
                />
              </Form.Item>

              <Form.Item
                name="foundedDate"
                label="Date Fondation"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
                getValueProps={(i) => ({value: dayjs(i)})}
              >
                <DatePicker style={{ width: '100%' }} format="YYYY-MM-DD" placeholder="Sélectionnez une date" />

              </Form.Item>



              <Form.Item
                name="banks"
                label="Banque"
                required={true}
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Select>
                  <option value="" key="" />
                  {banks ? banks.map(bank => (
                    <option value={bank.id} key={bank.id}>{bank.name}</option>
                  )) : null}
                </Select>
              </Form.Item>

              <Form.Item
                name="enabled"
                label='Statut'
                required={true}
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Select
                  //  placeholder={CustomPlaceholder}
                  style={{width: 600}}
                  //onChange={handleChange}
                >
                  <Select.Option value={true}>Actif</Select.Option>
                  <Select.Option value={false}>Inactif</Select.Option>
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

export default AgenciesUpdate;
