import React, { useState } from 'react';
import {Layout, Menu, Typography, Modal, Button, Dropdown, Space, Card, Divider} from 'antd';
import { Link, Outlet, useLocation, useNavigate } from 'react-router-dom';
import { Input } from 'antd';
import {
  ExclamationCircleOutlined,
  ApartmentOutlined,
  BankOutlined,
  BarChartOutlined,
  BellOutlined,
  CreditCardOutlined,
  DashboardOutlined,
  DollarOutlined,
  EnvironmentOutlined,
  FileTextOutlined,
  HistoryOutlined,
  InsuranceOutlined,
  LogoutOutlined,
  NotificationOutlined,
  ProductOutlined,
  SearchOutlined,
  PercentageOutlined,
  TeamOutlined,
  TransactionOutlined,
  UnorderedListOutlined,
  UserOutlined,
  DollarCircleOutlined,
  PhoneOutlined,
  MoreOutlined, IdcardOutlined
} from '@ant-design/icons';
import { hasAnyAuthority } from "app/shared/auth/private-route";
import { useAppSelector } from "app/config/store";
import { AUTHORITIES } from "app/config/constants";
import { deleteEntity } from "app/modules/dashboard/claim/claim.reducer";

const { Header, Content, Footer, Sider } = Layout;
const { Title,Text } = Typography;

const items = [
  { label: 'overview', key: '/dashboard', icon: <DashboardOutlined /> },
  { label: 'clients', key: '/dashboard/member-user', icon: <TeamOutlined /> },
  { label: 'produits', key: '/dashboard/product', icon:<ProductOutlined /> },
  { label: 'paiements', key: '/dashboard/payment', icon: <CreditCardOutlined   /> },
  { label: 'notifications', key: '/dashboard/notification', icon: <BellOutlined  /> },
  { label: 'factures', key: '/dashboard/invoice ', icon: <FileTextOutlined     /> },
  { label: 'rapports crédits', key: '/dashboard/credit_rapport', icon: <BarChartOutlined    /> },
  { label: 'contacts', key: '/dashboard/contact', icon: <PhoneOutlined  /> },
  { label: 'réclamations', key: '/dashboard/claim', icon: <ExclamationCircleOutlined   /> },
  { label: 'agences', key: '/dashboard/agencies', icon: <ApartmentOutlined  /> },
  { label: 'addresses', key: '/dashboard/address', icon: <EnvironmentOutlined  /> },
  { label: 'banques', key: '/dashboard/bank', icon: <BankOutlined   /> },
  { label: 'score crédit', key: '/dashboard/algoCreditScore', icon: <PercentageOutlined    /> },
];

const itemsCommercant = [
  { label: 'overview', key: '/dashboard', icon: <DashboardOutlined /> },
  { label: 'rechercher', key: '/dashboard/rechercher', icon: <SearchOutlined /> },
  { label: 'clients', key: '/dashboard/member-user-commercant', icon: <TeamOutlined /> },
  { label: 'produits', key: '/dashboard/product', icon:<ProductOutlined /> },
  { label: 'paiements', key: '/dashboard/payment', icon: <CreditCardOutlined   /> },
  { label: 'factures', key: '/dashboard/invoice ', icon: <FileTextOutlined     /> },
];

const itemsUser = [
  { label: 'overview', key: '/dashboard', icon: <DashboardOutlined /> },
  { label: 'réclamations', key: '/dashboard/claim-user', icon: <ExclamationCircleOutlined   /> },
];

const DashboardLayout = () => {
  const account = useAppSelector(state => state.authentication.account);
  const navigate = useNavigate();
  const location = useLocation();

  const [modalVisible, setModalVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const AUTHORITIES = {
    ADMIN: 'ROLE_ADMIN',
    USER: 'ROLE_USER',
    MERCHANT: 'ROLE_MERCHANT',
  };
  const roleMapping = {
    [AUTHORITIES.ADMIN]: 'Admin',
    [AUTHORITIES.USER]: 'User',
    [AUTHORITIES.MERCHANT]: 'Merchant',
  };

  const readableRole = roleMapping[account?.role]
  const handleCollapse = (collapsed) => {
   // setCollapsed(collapsed);
  };

  const handleLogout = () => {
    setModalVisible(true);
  };

  const handleOk = () => {
    setConfirmLoading(true);
    // Simulate logout action
    setTimeout(() => {
      setModalVisible(false);
      setConfirmLoading(false);
      // Redirect to homepage
      navigate("/logout");
    }, 2000); // Simulating logout process with setTimeout
  };

  const handleCancel = () => {
    setModalVisible(false);
  };


  const moreOutlinedStyle = {
    fontSize: '20px',
    color: '#555',
    boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
    backgroundColor: '#F7F0EF',
    padding: '5px',
    borderRadius: '50%',
  };

  const userIconStyle = {
    marginRight:8,
    fontSize: '24px',
    color: '#4C4646',  // Specify the color for the user icon
  };
  const menu = (
    <Menu>
      <Menu.Item key="profile" onClick={() => navigate('dashboard/member-user')}>
        <UserOutlined style={{ marginRight: '8px' }} /> Profile
      </Menu.Item>
      <Menu.Item key="logout" onClick={handleLogout}>
        <LogoutOutlined style={{ marginRight: '8px' }} /> Déconnexion
      </Menu.Item>
    </Menu>
  );

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header
        style={{
          height:85,
          width: '99%',
          borderRadius: 10,
          marginBottom: 5,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          padding: '10px 20px',
          background: '#fff',
          boxShadow: '0 1px 2px rgba(0, 0, 0, 0.1)',
        }}
      >
        <div style={{ marginLeft: -85, marginBottom: 8 }}>
          <img
            src="content/images/10.png"
            alt="Logo"
            style={{ width: '300px', height: '170px' }}
          />
        </div>
        <div style={{display: 'flex', alignItems: 'center',justifyContent:'center'}}>
          <div
            style={{
              height:70,
              width: 220,
              borderRadius: 10,
           //   backgroundColor: '#F9F2F2',
           //   boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
              border: '1px solid rgba(0, 0, 0, 0.05)',
              padding: '10px',
              display: 'flex',
              flexDirection:'row',
              justifyContent:'space-between',
              alignItems:'center'
            }}
          >
  <div style={{display:'flex',flexDirection:'row',alignItems:'center'}}>
              <UserOutlined style={userIconStyle}  />

              <div style={{display:'flex',flexDirection:'column'}}>
              <Title level={4} style={{ color: 'black',margin:0,padding:0 }}>



                {account?.login}
              </Title>
              <Text type="secondary" style={{marginTop:0}}>{hasAnyAuthority(account.authorities, [AUTHORITIES.ADMIN]) ? 'Administrateur' : hasAnyAuthority(account.authorities, [AUTHORITIES.MERCHANT]) ? 'Commercant' : 'Client'}</Text>
              </div>


  </div>
            <Dropdown overlay={menu} trigger={['click']}>
              <div
                style={{
                  //  marginLeft: '20px',
                  alignSelf:'center',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  //  padding: '5px',
                  borderRadius: '50%',
                 // backgroundColor: '#f0f0f0',
                  transition: 'background-color 0.3s',
                  // boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
                }}
                onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = '#e0e0e0')}
                onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = '#f0f0f0')}
              >
                <MoreOutlined style={{fontSize: '20px',
                  color: '#555',

                  //  backgroundColor: '#F7F0EF',
                  padding: '5px',
                  borderRadius: '50%',}} />
              </div>
            </Dropdown>

          </div>

        </div>
        <Modal
          title="Quitter l'application ?"
          visible={modalVisible}
          onOk={handleOk}
          confirmLoading={confirmLoading}
          onCancel={handleCancel}
          footer={[
            <Button key="cancel" onClick={handleCancel}>
              Annuler
            </Button>,
            <Button key="delete" type="primary" danger onClick={handleOk}>
              Quitter
            </Button>,
          ]}
        >
          <p>Êtes-vous certain de vouloir quitter ?</p>
        </Modal>
      </Header>
      <Layout>
        <Sider theme="light" collapsible  onCollapse={handleCollapse}>
          <Menu
            style={{ borderRadius: 10, height: '100%' }}
            onClick={(item) => navigate(item?.key)}
            defaultSelectedKeys={[location.pathname]}
            items={hasAnyAuthority(account.authorities, [AUTHORITIES.ADMIN]) ? items :
              hasAnyAuthority(account.authorities, [AUTHORITIES.MERCHANT]) ? itemsCommercant :
                itemsUser}
            mode="inline"
          >
          </Menu>
        </Sider>
        <Layout>
          <Layout className="site-layout">
            <Content style={{ padding: '0 20px', minHeight: 280 }}>
              <div>
                <Outlet />
              </div>
            </Content>
          </Layout>
        </Layout>
      </Layout>
    </Layout>
  );
};

export default DashboardLayout;
