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
import { getEntities, deleteEntity } from './payment.reducer';

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

const Payment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('');
  const [loadModal, setLoadModal] = useState(false);

  const paymentList = useAppSelector(state => state.payment.entities);
  const loading = useAppSelector(state => state.payment.loading);
  const paymentEntity = useAppSelector(state => state.payment.entity);
  const { id } = useParams<'id'>();
  const [deletePaymentId, setDeletePaymentId] = useState<string>('');

  const handleMenuClick: any = (e: any, id: string) => {
    if (e?.key === "edit") {
      navigate(`/dashboard/payment/${id}/edit`);
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
    dispatch(deleteEntity(deletePaymentId)); // Use the ID from state for deletion
    setTimeout(() => {
      setVisible(false);
      setConfirmLoading(false);
    });
  };
  const confirmDelete = (paymentId: string) => {
    setModalText(`Êtes-vous certain de vouloir supprimer le paiement ${paymentId} ?`);
    setVisible(true);
    setDeletePaymentId(paymentId); // Set the ID to delete in state
  };

  const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id', width: 80 },
    {
      title: 'Numéro chéque',
      dataIndex: 'checkNumber',
      key: 'checkNumber',
      render: (text) => text ? text : '-',
      width: 150,
    },
    { title: 'Émetteur', dataIndex: 'checkIssuer', key: 'checkIssuer', width: 150 },
    {
      title: 'Numéro compte',
      dataIndex: 'accountNumber',
      key: 'accountNumber',
      render: (text) => text ? text : '-',
      width: 200,
    },
    {
      title: 'Date chéque',
      dataIndex: 'checkDate',
      key: 'checkDate',
      width: 150,
      render: (text) => text ? moment(text).format('YYYY-MM-DD') : '-',
    },
    { title: 'Destinataire', dataIndex: 'recipient', key: 'recipient', width: 150 },
    {
      title: 'Date de signature',
      dataIndex: 'dateOfSignature',
      key: 'dateOfSignature',
      width: 150,
      render: (text) => text ? moment(text).format('YYYY-MM-DD') : '-',
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
      render: (text) => text ? moment(text).format('YYYY-MM-DD') : '-',
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
    {
      title: 'Action',
      dataIndex: 'id',
      width: 100,
      key: 'x',
      render: (text) => (
        <Dropdown
          menu={{ items, onClick: e => handleMenuClick(e, text) }}
        >
          <Button type="text" onClick={handleOkButtonClick}>
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

        <Title style={{marginBottom:0}} id="payment-heading" data-cy="PaymentHeading" level={2}>Paiements</Title>
          <Text type="secondary">
            Paiements - Gérez Vos Transactions Financières
          </Text>
          </Col>
        <div style={{marginTop:15}} className="d-flex justify-content-end">
          <Button className="me-2" onClick={handleSyncList}>Actualiser la liste</Button>
          <Button type="primary" onClick={() => navigate('/dashboard/payment/new')}>Créer un nouveau paiement</Button>
        </div>
      </div>
      <Table
        style={{ borderWidth: 2, borderColor: 'red', marginTop: 20, width: '100%' }}
        columns={columns}
        dataSource={paymentList}
        scroll={{ x: 'max-content' }}
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

export default Payment;
