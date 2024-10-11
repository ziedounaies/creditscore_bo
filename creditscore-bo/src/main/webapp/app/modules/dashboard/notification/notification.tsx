import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {Table, Button, Dropdown, MenuProps, Space, Pagination, Tag, Modal, Typography, Col} from 'antd';
import type { TableColumnsType } from 'antd';
import { EditOutlined, DeleteOutlined, MoreOutlined } from '@ant-design/icons';
import moment from 'moment';
import { getEntities, deleteEntity } from './notification.reducer';

const { Title,Text } = Typography;

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

const Notification = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('');
  const [loadModal, setLoadModal] = useState(false);

  const notificationList = useAppSelector(state => state.notification.entities);
  const loading = useAppSelector(state => state.notification.loading);
  const notificationEntity = useAppSelector(state => state.notification.entity);
  const { id } = useParams<'id'>();
  const [deleteNotificationId, setDeleteNotificationId] = useState<string>('');


  const handleMenuClick: any = (e: any, id: string) => {
    if (e?.key === "edit") {
      navigate(`/dashboard/notification/${id}/edit`);
    } else if (e?.key === "delete") {
      confirmDelete(id); // Pass the ID to the confirmDelete function
    }
    console.log('click menu', e);
  };

  const handleCancelButtonClick = () => {
    setVisible(false);
  };

  const handleOkButtonClick = () => {
    setModalText('The modal will be closed after two seconds');
    setConfirmLoading(true);
    dispatch(deleteEntity(deleteNotificationId)); // Use the ID from state for deletion
    setTimeout(() => {
      setVisible(false);
      setConfirmLoading(false);
    });
  };
  const confirmDelete = (notificationId: string) => {
    setModalText(`Êtes-vous certain de vouloir supprimer la notification ${notificationId} ?`);
    setVisible(true);
    setDeleteNotificationId(notificationId); // Set the ID to delete in state
  };

  const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Titre', dataIndex: 'title', key: 'title' },
    {
      title: 'Message',
      dataIndex: 'message',
      key: 'message',
      render: (text: string) => (
        <span title={text}>
      {text.length > 50 ? `${text.substring(0, 50)}...` : text}
    </span>
      ),
    },

      {
        title: 'Lu/Non lu',
        dataIndex: 'readed',
        key: 'readed',
        render: (enabled: boolean) => (
          <Tag color={enabled ? 'green' : 'red'}>
            {enabled ? 'Lu' : 'Non lu'}
          </Tag>
        ),
      },


    {
      title: 'Action',
      dataIndex: 'id',
      width: 90,
      key: 'x',
      render: (text: string) => <Dropdown menu={{
        items,
        onClick: e => handleMenuClick(e, text),
      }}>
        <Button type="text" onClick={handleOkButtonClick} >
          <Space>
            <MoreOutlined />
          </Space>
        </Button>
      </Dropdown>,
    },
  ];

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
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
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
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

  return (
    <div>
      <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
        <Col>
        <Title style={{ marginBottom: 0 }} id="notification-heading" data-cy="NotificationHeading" level={2}>Notifications</Title>

          <Text type="secondary">
            Réclamations - Exprimez Vos Préoccupations
          </Text>
          </Col>
        <div style={{marginTop:15}} className="d-flex justify-content-end">
          <Button className="me-2" onClick={handleSyncList}>Actualiser la liste</Button>
          <Button type="primary" onClick={() => navigate('/dashboard/notification/new')}>Créer une nouvelle notification</Button>
        </div>
      </div>
      <Table
        style={{ borderWidth: 2, borderColor: 'red',marginTop:20 }}
        columns={columns}
        dataSource={notificationList}
      />
      <Modal
        title="Confirmation de suppression"
        visible={visible}
        onOk={handleOkButtonClick}
        confirmLoading={confirmLoading}
        onCancel={handleCancelButtonClick}
        footer={[
          <Button key="cancel" onClick={handleCancelButtonClick}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Annuler
          </Button>,
          <Button key="delete" type="primary" danger onClick={handleOkButtonClick}>
            <FontAwesomeIcon icon="trash" />
            &nbsp; Supprimer
          </Button>,
        ]}
      >
        <p>{modalText}</p>
      </Modal>
    </div>
  );
};

export default Notification;
