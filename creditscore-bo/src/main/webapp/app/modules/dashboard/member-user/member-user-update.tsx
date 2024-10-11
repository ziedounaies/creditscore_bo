import React, { useEffect, useState } from 'react';
import {
  Tabs,
  Card,
  Row,
  Col,
  Form,
  Input,
  Select,
  DatePicker,
  Divider,
  Typography,
  Button,
  Table,
  Tag,
  Space, Dropdown,
} from 'antd';
import {
  ProductOutlined,
  FileTextOutlined,
  UserOutlined,
  EnvironmentOutlined,
  BarChartOutlined,
  IdcardOutlined,
  PhoneOutlined,
  BankOutlined,
  KeyOutlined,
  UsergroupAddOutlined,
  DollarOutlined,
  DollarCircleOutlined,
  LineChartOutlined,
  CreditCardOutlined,
  MoreOutlined,
} from '@ant-design/icons';
import moment from 'moment';
import type { TableColumnsType } from 'antd';
import { AcountType } from 'app/shared/model/enumerations/acount-type.model';
import { IdentifierType } from 'app/shared/model/enumerations/identifier-type.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { getEntity, updateEntity, createEntity, reset } from './member-user.reducer';
import {getEntities as getEntitiesContact} from "app/modules/dashboard/contact/contact.reducer";
import {getEntities as getEntitiesAddress} from "app/modules/dashboard/address/address.reducer";
import dayjs from "dayjs";
import {getEntities as getEntitiesInvoices} from "app/modules/dashboard/invoice/invoice.reducer";
import {getEntities as getEntitiesPayements} from "app/modules/dashboard/payment/payment.reducer";
import {getEntities as getEntitiesRapport} from "app/modules/dashboard/credit-rapport/credit-rapport.reducer";


const { Title, Text } = Typography;
const { TabPane } = Tabs;

const MemberUserUpdate = () => {
  const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Type', dataIndex: 'type', key: 'type',render: (text:string) => text === "PHONE_NUMBER" ? <Tag color="cyan">Numéro téléphone </Tag> :
        text === "EMAIL" ? <Tag color="Turquoise">Email</Tag> : <Tag color="geekblue">Fax</Tag>
    },
    { title: 'Valeur', dataIndex: 'value', key: 'value' },
  ];

  const columnsAddresses: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Rue', dataIndex: 'street', key: 'street' },
    { title: 'Ville', dataIndex: 'city', key: 'city' },
    { title: 'Code postal', dataIndex: 'zipCode', key: 'zipCode' },
  ];



  const columnsInvoice: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Numéro facture', dataIndex: 'invoiceNumber', key: 'invoiceNumber' },
    { title: 'Montant', dataIndex: 'amount', key: 'amount' },
    { title: 'Statut', dataIndex: 'status', key: 'status' ,render: (text:string) => text === "PENDING" ? <Tag color="#C19A6B">En attente</Tag> :
        text === "IN_PROGRESS" ? <Tag color="#FFA500">En cours</Tag>:
          text === "DONE" ? <Tag color="green">Payé</Tag> : <Tag color="Crimson">Impayé</Tag>
    },
  ];

  const columnsRapport: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Age compte', dataIndex: 'accountAge', key: 'accountAge' },
    { title: 'Limite crédit', dataIndex: 'creditLimit', key: 'creditLimit' },
    { title: 'score de crédit', dataIndex: 'creditScore', key: 'creditScore' },

    { title: 'Demandes de renseignements', dataIndex: 'inquiriesAndRequests', key: 'inquiriesAndRequests' },
  ];

  const columnsPayment: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id', width: 80 },
    {
      title: 'Numéro chéque',
      dataIndex: 'checkNumber',
      key: 'checkNumber',
      render: (text) => <div style={{ whiteSpace: 'nowrap' }}>{text}</div>,
      width: 200,
    },
    { title: 'Émetteur', dataIndex: 'checkIssuer', key: 'checkIssuer', width: 150 },
    {
      title: 'Numéro compte',
      dataIndex: 'accountNumber',
      key: 'accountNumber',
      render: (text) => <div>{text}</div>,
      width: 200,
    },
    {
      title: 'Date chéque',
      dataIndex: 'checkDate',
      key: 'checkDate',
      width: 150,
      render: (text, record) => {
        const formattedDate = moment(text).format('YYYY-MM-DD');
        return <div>{formattedDate}</div>;
      },
    },
    { title: 'Destinataire', dataIndex: 'recipient', key: 'recipient', width: 150 },
    {
      title: 'Date de signature',
      dataIndex: 'dateOfSignature',
      key: 'dateOfSignature',
      width: 150,
      render: (text, record) => moment(text).format('YYYY-MM-DD'),
    },
    {
      title: 'Méthode paiement',
      dataIndex: 'paymentMethod',
      key: 'paymentMethod',
      width: 170,
      render: (text) =>
        text === 'CHECK' ? <Tag color="Tan">Chéque</Tag> :
          text === 'CASH' ? <Tag color="#F4A460">Espèce</Tag> :
            <Tag color="#CD7F32">Debit</Tag>,
    },
    { title: 'Montant', dataIndex: 'amount', key: 'amount', width: 150,  render: (text) => text && `${text?.replace(/\B(?=(\d{3})+(?!\d))/g, " ")} TND`, },
    {
      title: 'Date paiement prévue',
      dataIndex: 'expectedPaymentDate',
      key: 'expectedPaymentDate',
      width: 180,
      render: text => <span>{moment(text).format('YYYY-MM-DD')}</span>,
    },
    {
      title: 'Date paiement effectué',
      dataIndex: 'datePaymentMade',
      key: 'datePaymentMade',
      width: 185,
      render: text => <span>{moment(text).format('YYYY-MM-DD')}</span>,
    },
    { title: 'Device', dataIndex: 'currency', key: 'currency', width: 150, render: text => <span>{text}</span> },
    {
      title: 'Statut',
      dataIndex: 'status',
      key: 'status',
      width: 150,
      render: (text) =>
        text === 'PENDING' ? <Tag color="#C19A6B">En attente</Tag> :
          text === 'IN_PROGRESS' ? <Tag color="#FFA500">En cours</Tag> :
            text === 'DONE' ? <Tag color="green">Payé</Tag> :
              <Tag color="Crimson">Impayé</Tag>,
    },
  ];

  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const [form] = Form.useForm();
  const invoiceList = useAppSelector(state => state.invoice.entities);
  const paymentList = useAppSelector(state => state.payment.entities);
  const productList = useAppSelector(state => state.product.entities);
  const addressList = useAppSelector(state => state.address.entities);
  const contactList = useAppSelector(state => state.contact.entities);
  const creditRapportList = useAppSelector(state => state.creditRapport.entities);

  const memberUserEntity = useAppSelector(state => state.memberUser.entity);
  const loading = useAppSelector(state => state.memberUser.loading);
  const updateSuccess = useAppSelector(state => state.memberUser.updateSuccess);
  const [mode, setMode] = useState<'edit' | 'save'>('edit'); // State variable to track mode

  // Other code remains unchanged
  const handleSaveButtonClick = () => {
    setActiveTabs(['informations']);

  }


  useEffect(() => {
    if (updateSuccess) {
      navigate('/dashboard/member-user');
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
      dispatch(
        getEntitiesContact({
         query:`memberUserId.equals=${id}`
        }),
      );
      dispatch(
        getEntitiesAddress({
          query:`memberUserId.equals=${id}`
        }),
      );
      dispatch(
        getEntitiesInvoices({
          query:`memberUserId.equals=${id}`
        }),
      );
      dispatch(
        getEntitiesPayements({
          query:`memberUserId.equals=${id}`
        }),
      );
      dispatch(
        getEntitiesRapport({
          query:`memberUserId.equals=${id}`
        }),
      );
    } else {
      dispatch(reset());
    }
  }, [id]);


  const onFinish = values => {
    const entity = {
      ...values,
      dateBirth: values.dateBirth ? dayjs(values.dateBirth) : null,
    };
    console.log(entity);
    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity({ id, ...entity }));
    }
  };

  const defaultValues = () => {
   return  isNew
      ? {
        role: 'CUSTOMER',
        typeContact: 'PHONENUMBER',
        dateBirth: null,
        enabled: true, // Default to active
      }
      : {
        ...memberUserEntity,
        dateBirth: memberUserEntity.dateBirth ? dayjs(memberUserEntity.dateBirth) : null,
      };
  }

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        role: 'CUSTOMER',
        typeContact: 'PHONENUMBER',
        dateBirth: null,
        enabled: true, // Default to active
      })
      : form.setFieldsValue({
        ...memberUserEntity,
        dateBirth: memberUserEntity.dateBirth ? dayjs(memberUserEntity.dateBirth) : null,
      });
  }, [memberUserEntity]);


  const CustomPlaceholderRole = (
    <span>
      <UserOutlined style={{ marginRight: 8, color: 'black' }} />
      Veuillez renseigner ce champ
    </span>
  );

  const CustomPlaceholderTypeAccount = (
    <span>
      <BankOutlined style={{ marginRight: 8, color: 'black' }} />
      Sélectionnez le type compte
    </span>
  );

  const [activeTab, setActiveTab] = useState('1');

  const acountTypeValues = Object.keys(AcountType);
  const identifierTypeValues = Object.keys(IdentifierType);
  const roleValues = Object.keys(Role);

  const handleChangeTab = key => {
    setActiveTab(key);
  };


    const [activeTabs, setActiveTabs] = useState<string[]>(['informations']);


  const handleEditButtonClick = () => {
    setActiveTabs(['informations', 'contact', 'addresses', 'rapport']);
  };
    const renderFormFields = () => (

      <Card style={{ borderRadius: 10, width: '100%', height: 800, overflowY: 'scroll' }}>
        <Form layout="vertical" name="memberUserForm" onFinish={onFinish} initialValues={defaultValues()} form={form}>
          <Row>
            <Col span={11}>
              <Form.Item
                label="prénom"
                name="firstName"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="prénom" />
              </Form.Item>
              <Form.Item
                label="nom famille"
                name="lastName"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<IdcardOutlined className="site-form-item-icon" />} placeholder="nom famille" />
              </Form.Item>
              <Form.Item
                label="Nom utilisateur"
                name="userName"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="nom utilisateur" />
              </Form.Item>
              <Form.Item
                label="Role"
                name="role"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select placeholder={CustomPlaceholderRole} style={{ width: "100%" }}>
                  {roleValues.map(role => (
                    <Select.Option value={role} key={role}>
                      {role.toLowerCase()}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>
              <Form.Item
                label="Date de naissance"
                name="birthDate"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
                getValueProps={(i) => ({value: dayjs(i)})}
              >
                <DatePicker style={{ width: '100%' }} format="YYYY-MM-DD" placeholder="Sélectionnez une date" />
              </Form.Item>
              <Form.Item
                label="Type identifiant"
                name="identifierType"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select style={{ width: 565 }}>
                  {identifierTypeValues.map(identifierType => (
                    <Select.Option value={identifierType} key={identifierType}>
                      {identifierType.toLowerCase()}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>
              <Form.Item
                label="Identifiant Valeur"
                name="identifierValue"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<KeyOutlined className="site-form-item-icon" />} placeholder="identifiant Valeur" />
              </Form.Item>
              <Form.Item
                label="Statut emploi"
                name="employersReported"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input
                  prefix={<UsergroupAddOutlined className="site-form-item-icon" />}
                  placeholder="statut emploi"
                />
              </Form.Item>
            </Col>
            <Divider type="vertical" style={{ height: 700 }} />
            <Col span={11} style={{ marginLeft: 10 }}>
              <Form.Item
                label="Nom entreprise"
                name="businessName"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<BankOutlined className="site-form-item-icon" />} placeholder="Nom entreprise" />
              </Form.Item>
              <Form.Item
                label="Revenu"
                name="income"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<DollarOutlined className="site-form-item-icon" />} placeholder="revenu" />
              </Form.Item>
              <Form.Item
                label="Dépenses"
                name="expenses"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<CreditCardOutlined className="site-form-item-icon" />} placeholder="expenses" />
              </Form.Item>
              <Form.Item
                label="Bénéfice brut"
                name="grossProfit"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<DollarCircleOutlined className="site-form-item-icon" />} placeholder="bénéfice brut" />
              </Form.Item>
              <Form.Item
                label="Marge bénéficiaire nette"
                name="netProfitMargin"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<LineChartOutlined className="site-form-item-icon" />} placeholder="marge bénéficiaire nette" />
              </Form.Item>
              <Form.Item
                label="Dette Obligations"
                name="debtsObligations"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Input prefix={<CreditCardOutlined className="site-form-item-icon" />} placeholder="dette Obligations" />
              </Form.Item>
              <Form.Item
                label="Type compte"
                name="acountType"
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]}
              >
                <Select placeholder={CustomPlaceholderTypeAccount} style={{ width: 565 }}>
                  {acountTypeValues.map(acountType => (
                    <Select.Option value={acountType} key={acountType}>
                      {acountType}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>
              <Form.Item
                name="enabled"
                label="Actif/Inactif"
                required={true}
                rules={[{ required: true, message: 'Veuillez renseigner ce champ !' }]} >
              <Select style={{ width: 565 }}>
                <Select.Option value={true}>Actif</Select.Option>
                <Select.Option value={false}>Inactif</Select.Option>
              </Select>
            </Form.Item>

        </Col>
      </Row>
        </Form>
    </Card>
  );


  const renderFormFieldsContact = () => (

    <Card style={{ borderRadius: 10, width: '100%', height: 690, overflowY: 'scroll' }}>

      <Table
        style={{ borderWidth: 2, borderColor: 'red' ,marginTop:20}}
        columns={columns}
        dataSource={contactList}
      />

    </Card>

  );

  const renderFormFieldsAddresses = () => (
    <Card style={{ borderRadius: 10, width: '100%', height: 690, overflowY: 'scroll' }}>
      <Table
        style={{ borderWidth: 2, borderColor: 'red' ,marginTop:20}}
        columns={columnsAddresses}
        dataSource={addressList}
      />
    </Card>
  );

  const renderFormFieldsRapport = () => (
    <Card style={{ borderRadius: 10, width: '100%', height: 690, overflowY: 'scroll' }}>
      <Tabs defaultActiveKey="1" onChange={handleChangeTab}>


        <TabPane tab={
          <span>
            <FileTextOutlined style={{marginRight:5}} />
            Factures
          </span>
        } key="2">

          <Table
            style={{ borderWidth: 2, borderColor: 'red',marginTop:20 }}
            columns={columnsInvoice}
            dataSource={invoiceList}
          />

        </TabPane>

        <TabPane tab={
          <span>
            <CreditCardOutlined style={{marginRight:5}} />
            paiements
          </span>
        } key="3">
          <Table
            style={{ borderWidth: 2, borderColor: 'red' ,marginTop:20}}
            columns={columnsPayment}
            dataSource={paymentList}
            scroll={{x:1300}}
          />
        </TabPane>

        <TabPane tab={
          <span>
            <CreditCardOutlined style={{marginRight:5}} />
            Détails du rapport de drédit
          </span>
        } key="4">
          <Table
            style={{ borderWidth: 2, borderColor: 'red' ,marginTop:20}}
            columns={columnsRapport}
            dataSource={creditRapportList}
            scroll={{x:1300}}
          />
        </TabPane>


      </Tabs>

    </Card>
  );



  return (
    <Card style={{ borderRadius: 10, width: '100%', height: 690, overflowY: 'scroll' }}>
      <Row justify="space-between" style={{ marginBottom: 15 }}>
        <Col>
          <Title level={2} style={{ marginBottom: 0 }}>
            {isNew ? `Créer un client` : `Fiche client`}
          </Title>
          <Text type="secondary">Espace Client - Gérez Votre Compte en Toute Simplicité</Text>
        </Col>
        <Col>
          <div style={{ display: 'flex', flexDirection: 'row' }}>
            <Link to="/dashboard/member-user">
              <Button
                className="me-2"
                style={{ borderRadius: 10, backgroundColor: 'white', color: '#333' }}
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Retour</span>
              </Button>
            </Link>
            <Button
              type="primary"
              style={{ borderRadius: 10, backgroundColor: '#FF3029' }}
              htmlType="submit"
              form="memberUserForm"
            >
              <FontAwesomeIcon icon="save" />
              &nbsp; Sauvegarder
            </Button>
          </div>
        </Col>
      </Row>

      <Tabs defaultActiveKey="1" onChange={handleChangeTab}>
        <TabPane
          tab={
            <span>
      <UserOutlined style={{ marginRight: 5 }} />
   Informations personnelles
    </span>
          }
          key="1"
        >
          {renderFormFields()} {/* Call the function here */}
        </TabPane>

        {!isNew && <>
          <TabPane tab={
            <span>
            <PhoneOutlined style={{marginRight: 5}}/>
            Contact
          </span>
          } key="2">
            {renderFormFieldsContact()}
          </TabPane>

          <TabPane tab={
            <span>
            <EnvironmentOutlined style={{marginRight: 5}}/>
            Addresses
          </span>
          } key="3">
            {renderFormFieldsAddresses()}
          </TabPane>

          <TabPane tab={
            <span>
            <BarChartOutlined style={{marginRight: 5}}/>
            rapport de credit
          </span>
          } key="4">
            {renderFormFieldsRapport()}
          </TabPane>
        </>}
      </Tabs>


    </Card>
  );
};

export default MemberUserUpdate;
