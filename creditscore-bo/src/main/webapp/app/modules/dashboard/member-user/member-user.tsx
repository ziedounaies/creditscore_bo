import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Table, Button, Dropdown, MenuProps, Space, Pagination, Tag, Modal, Typography, Divider, Tabs } from 'antd';
import type { TableColumnsType } from 'antd';
import { EditOutlined, DeleteOutlined, MoreOutlined } from '@ant-design/icons';
import moment from 'moment';
import { getEntities, deleteEntity } from './member-user.reducer';
import MemberUserUpdate from "app/entities/member-user/member-user-update";

const { Title, Text } = Typography;
const { TabPane } = Tabs;

const items: MenuProps['items'] = [
  {
    label: 'Modifier',
    key: 'edit',
    icon: <EditOutlined />,
  },
  {
    label: 'Supprimer',
    key: 'delete',
    icon: <DeleteOutlined />,
    danger: true,
  },
];

const MemberUser = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const [sortState, setSortState] = useState(
    overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search)
  );
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('');
  const [loadModal, setLoadModal] = useState(false);
  const memberUserList = useAppSelector(state => state.memberUser.entities);
  const loading = useAppSelector(state => state.memberUser.loading);
  const memberUserEntity = useAppSelector(state => state.memberUser.entity);
  const { id } = useParams<'id'>();
  const [deleteUserId, setDeleteUserId] = useState<string>('');
  const [activeTabs, setActiveTabs] = useState<string[]>(['informations']);

  const handleMenuClick = (e: any, id: string) => {
    if (e?.key === 'edit') {
      navigate(`/dashboard/member-user/${id}/edit`, {
        state: { memberUserData: memberUserList.find(user => user.id === id) },
      });
      setActiveTabs(['informations', 'contact', 'addresses', 'rapport']); // Set activeTabs state to all tabs
    } else if (e?.key === 'delete') {
      confirmDelete(id);
    }
    console.log('click menu', e);
  };

  const handleCancelButtonClick = () => {
    setVisible(false);
  };

  const handleOkButtonClick = () => {
    setModalText('The modal will be closed after two seconds');
    setConfirmLoading(true);
    dispatch(deleteEntity(deleteUserId));
    setTimeout(() => {
      setVisible(false);
      setConfirmLoading(false);
    });
  };

  const confirmDelete = (userId: string) => {
    setModalText(`Êtes-vous certain de vouloir supprimer le Member User ${userId} ?`);
    setVisible(true);
    setDeleteUserId(userId);
  };
  const handleSaveButtonClick = () => {
    setActiveTabs(['informations']);
  };
  const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    {
      title: 'Type compte',
      dataIndex: 'acountType',
      key: 'acountType',
      render: (text: string) =>
        text === 'PHYSICAL_PERSON' ? <Tag color="#228B22">Personne physique</Tag> : <Tag color="#014421">Société</Tag>,
    },
    { title: 'Nom utilisateur', dataIndex: 'userName', key: 'userName' },
    {
      title: `Pièce d'identité`,
      dataIndex: 'identifierType',
      key: 'identifierType',
      render: (text: string) =>
        text === 'CIN' ? <Tag color="#004040">Carte d'identité nationale</Tag> : text === 'PASSPORT' ? <Tag color="Teal">Passeport</Tag> : <Tag color="#00CCCC">Matricule fiscale</Tag>,
    },
    { title: `N° pièce d'identité`, dataIndex: 'identifierValue', key: 'identifierValue' },
    {
      title: 'Rôle',
      dataIndex: 'role',
      key: 'role',
      render: (text: string) =>
        text === 'CUSTOMER' ? <Tag color="blue">Client</Tag> : text === 'COMMERCANT' ? <Tag color="red">Commercant</Tag> : <Tag color="purple">Admin</Tag>,
    },
    {
      title: 'Actif/Inactif',
      dataIndex: 'enabled',
      key: 'enabled',
      render: (enabled: boolean) => (
        <Tag color={enabled ? 'green' : 'red'}> {enabled ? 'Actif' : 'Inactif'} </Tag>
      ),
    },
    {
      title: 'Action',
      dataIndex: 'id',
      width: 90,
      key: 'x',
      render: (text: string) => (
        <Dropdown menu={{ items, onClick: e => handleMenuClick(e, text) }}>
          <Button type="text">
            <Space>
              <MoreOutlined />
            </Space>
          </Button>
        </Dropdown>
      ),
    },
  ];

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({ ...sortState, order: sortState.order === ASC ? DESC : ASC, sort: p });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  const handleCreateNewClient = () => {
    navigate('/dashboard/member-user/new');
    setActiveTabs(['informations']); // Set activeTabs state to only 'informations'
  };

  return (
    <div>
      <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', paddingTop: 5, paddingBottom: 20 }}>
        <div>
          <Title id="member-user-heading" data-cy="MemberUserHeading" level={2} style={{ marginBottom: 0 }}>
            Liste des utilisateurs
          </Title>
          <Text type="secondary"> Espace Client - Gérez Votre Compte en Toute Simplicité </Text>
        </div>
        <div className="d-flex justify-content-end">
          <Button className="me-2" onClick={handleSyncList}>Actualiser la liste</Button>
          <Button type="primary" onClick={handleCreateNewClient}>Créer un nouveau client</Button>
        </div>
      </div>
      <Table style={{ borderWidth: 2, borderColor: 'red' }} columns={columns} dataSource={memberUserList} scroll={{ x: 1300 }} />
      <Modal
        title="Confirmation de suppression"
        visible={visible}
        onOk={handleOkButtonClick}
        confirmLoading={confirmLoading}
        onCancel={handleCancelButtonClick}
        footer={[
          <Button key="cancel" onClick={handleCancelButtonClick}>
            <FontAwesomeIcon icon="ban" /> &nbsp; Annuler
          </Button>,
          <Button key="delete" type="primary" danger onClick={handleOkButtonClick}>
            <FontAwesomeIcon icon="trash" /> &nbsp; Supprimer
          </Button>,
        ]}
      >
        <p>{modalText}</p>
      </Modal>
    </div>
  );
};

export default MemberUser;
