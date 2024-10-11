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
import { getEntities, deleteEntity } from './claim.reducer';

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

const Claim = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('');
  const [loadModal, setLoadModal] = useState(false);

  const claimList = useAppSelector(state => state.claim.entities);
  const loading = useAppSelector(state => state.claim.loading);
  const claimEntity = useAppSelector(state => state.claim.entity);
  const { id } = useParams<'id'>();
  const [deleteClaimId, setDeleteClaimId] = useState<string>('');




  const handleCancelButtonClick = () => {
    setVisible(false);
  };

  const handleOkButtonClick = () => {
    setModalText('The modal will be closed after two seconds');
    setConfirmLoading(true);
    dispatch(deleteEntity(deleteClaimId)); // Use the ID from state for deletion
    setTimeout(() => {
      setVisible(false);
      setConfirmLoading(false);
    });
  };
  const confirmDelete = (claimId: string) => {
    setModalText(`Êtes-vous certain de vouloir supprimer la réclamation ${claimId} ?`);
    setVisible(true);
    setDeleteClaimId(claimId); // Set the ID to delete in state
  };

  const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Statut', dataIndex: 'status', key: 'status' ,render: (text:string) => text === "PENDING" ? <Tag color="#C19A6B">En attente</Tag> :
        text === "IN_PROGRESS" ? <Tag color="#FFA500">En cours</Tag>:
          text === "DONE" ? <Tag color="green">Traitée</Tag> : <Tag color="Crimson">Rejetée</Tag>
    },
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
    }
  ];

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
        query: `memberUserId.equals=10`
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
        <Title style={{marginBottom:0}} id="claim-heading" data-cy="ClaimHeading" level={2}>Réclamations</Title>
        <Text type="secondary">
          Gérez vos réclamations en toute simplicité
        </Text>
        </Col>
        <div style={{marginTop:15}} className="d-flex justify-content-end">
          <Button className="me-2" onClick={handleSyncList}>Actualiser la liste</Button>
          <Button type="primary" onClick={() => navigate('/dashboard/claim-user/new')}>Créer une nouvelle réclamation</Button>
        </div>
      </div>
      <Table
        style={{ borderWidth: 2, borderColor: 'red' ,marginTop:20}}
        columns={columns}
        dataSource={claimList}
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

export default Claim;
