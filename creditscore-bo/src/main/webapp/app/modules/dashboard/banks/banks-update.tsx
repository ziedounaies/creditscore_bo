import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, Form, Input, Card, Divider, Typography, Select, DatePicker } from 'antd';
import moment from 'moment';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, updateEntity, createEntity, reset } from './banks.reducer';
import { BankOutlined, ClusterOutlined } from "@ant-design/icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {convertDateTimeFromServer, displayDefaultDateTime} from "app/shared/util/date-utils";

const { Title, Text } = Typography;

const BanksUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const banksEntity = useAppSelector(state => state.banks.entity);
  const updateSuccess = useAppSelector(state => state.banks.updateSuccess);
  const [form] = Form.useForm();

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    } else {
      dispatch(reset());
    }
  }, [id]);

  useEffect(() => {
    if (updateSuccess) {
      navigate('/dashboard/bank');
    }
  }, [updateSuccess]);

  const onFinish = values => {
    const formattedValues = {
      ...values,
      f: values.foundedDate ? values.foundedDate.format() : null,
    };

    if (isNew) {
      dispatch(createEntity(formattedValues));
    } else {
      dispatch(updateEntity({ id, ...formattedValues }));
    }
  };


  const saveEntity = values => {
    // Assuming `enabled` corresponds to the Actif/Inactif status
    const isEnabled = values.enabled === true; // true for "Actif", false for "Inactif"

    const entity = {
      ...banksEntity,
      ...values,
      enabled: isEnabled,
      foundedDate: values.foundedDate ? values.foundedDate.format() : null,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };


  const defaultValues = () =>
    isNew
      ? {}
      : {
        ...banksEntity,
        foundedDate: banksEntity.foundedDate ? moment(banksEntity.foundedDate) : null,
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({

      })
      : form.setFieldsValue({
        ...banksEntity,
        foundedDate: banksEntity.foundedDate ? moment(banksEntity.foundedDate) : null,
      });
  }, [banksEntity]);

  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%', height: 690 }}>
        <Row justify="space-between" style={{ marginBottom: 15 }}>
          <Col>
            <Title level={2} style={{ marginBottom: 0 }}>
              {isNew ? `Créer une banque` : `Fiche banque`}
            </Title>
            <Text type="secondary">
              Profils Bancaires - Découvrez les Institutions Financières
            </Text>
          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/bank">
                <Button className="me-2" style={{ borderRadius: 10, backgroundColor: 'white', color: '#333' }}>
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Retour</span>
                </Button>
              </Link>
              <Button type="primary" style={{ borderRadius: 10, backgroundColor: '#FF3029' }} htmlType="submit" form="banksForm">
                <FontAwesomeIcon icon="save" style={{marginRight:7}} />     Sauvegarder
                 </Button>
            </div>
          </Col>
        </Row>
        <Divider />
        <Row gutter={[16, 16]}>
          <Col span={12}>
            <Form layout="vertical" name="banksForm" onFinish={onFinish} initialValues={defaultValues()} form={form}>
              <Form.Item label="Nom banque" name="name" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<BankOutlined className="site-form-item-icon" />} placeholder="Nom banque" />
              </Form.Item>

              <Form.Item label="Date Fondation" name="foundedDate" rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <DatePicker style={{ width: '100%' }} format="YYYY-MM-DD" placeholder="Sélectionnez une date" />
              </Form.Item>


              <Form.Item label="Branches" id="banks-branches" name="branches"
                         rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}>
                <Input prefix={<ClusterOutlined className="site-form-item-icon" />} placeholder="Branches" />
              </Form.Item>
              <Form.Item
               id="banks-enabled" name="enabled" data-cy="enabled"
                label='Actif/Inactif'
                required={true}
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Select style={{ width: '100%' }}>
                  <Select.Option value={true}>Actif</Select.Option>
                  <Select.Option value={false}>Inactif</Select.Option>
                </Select>
              </Form.Item>
            </Form>
          </Col>
          <Col span={1}>
            <Divider type="vertical" style={{ height: 525 }} />
          </Col>
          <Col span={11}>

          </Col>
        </Row>

      </Card>
    </div>
  );
};

export default BanksUpdate;
