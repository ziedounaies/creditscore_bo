import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate, useParams } from 'react-router-dom';
import {Translate, TextFormat, getSortState, getPaginationState} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import {ASC, DESC, ITEMS_PER_PAGE, SORT} from 'app/shared/util/pagination.constants';
import {overridePaginationStateWithQueryParams, overrideSortStateWithQueryParams} from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {Table, Button, Dropdown, MenuProps, Space, Pagination, Tag, Modal, Typography, Col} from 'antd';
import type { TableColumnsType } from 'antd';
import { EditOutlined, DeleteOutlined, MoreOutlined } from '@ant-design/icons';
import moment from 'moment';
import { getEntities, deleteEntity } from './agencies.reducer';

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

const Agencies = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('');
  const [loadModal, setLoadModal] = useState(false);

  const agenciesList = useAppSelector(state => state.agencies.entities);
  const loading = useAppSelector(state => state.agencies.loading);
  const agenciesEntity = useAppSelector(state => state.agencies.entity);
  const totalItems = useAppSelector(state => state.agencies.totalItems);
  const { id } = useParams<'id'>();
  const [deleteAgenciesId, setDeleteAgenciesId] = useState<string>('');


  const handleMenuClick: any = (e: any, id: string) => {
    if (e?.key === "edit") {
      navigate(`/dashboard/agencies/${id}/edit`);
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
    dispatch(deleteEntity(deleteAgenciesId)).then(() => {
      setVisible(false);
      setConfirmLoading(false);
      // After deletion, navigate back to the Contact page
      navigate('/dashboard/agencies'); // Assuming '/dashboard/contact' is the correct route
    });
  };
  const confirmDelete = (agenciesId: string) => {
    setModalText(`Êtes-vous certain de vouloir supprimer l'agence ${agenciesId} ?`);
    setVisible(true);
    setDeleteAgenciesId(agenciesId); // Set the ID to delete in state
  };


  const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Nom', dataIndex: 'name', key: 'name' },
    {
      title: 'Date de création',
      dataIndex: 'foundedDate',
      key: 'foundedDate',
      render: (text, record) => moment(text).format('DD/MM/YYYY'),
    },


    {
      title: 'Actif/Inactif',
      dataIndex: 'enabled',
      key: 'enabled',
      render: (enabled: boolean) => (
        enabled ? <Tag color="green">Actif</Tag> : <Tag color="red">Inactif</Tag>
      )
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
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

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
        <Title style={{marginBottom:0}} id="agencies-heading" data-cy="AgenciesHeading" level={2}>Agences</Title>
        <Text type="secondary">
          Agences Bancaires - Découvrez Nos Points de Service
        </Text>
          </Col>
        <div  style={{marginTop:15}} className="d-flex justify-content-end">
          <Button className="me-2" onClick={handleSyncList}>Actualiser la liste</Button>
          <Button type="primary" onClick={() => navigate('/dashboard/agencies/new')}>Créer une nouvelle agence</Button>
        </div>
      </div>
      <Table
        style={{ borderWidth: 2, borderColor: 'red' ,marginTop:20}}
        columns={columns}
        dataSource={agenciesList}
        pagination={{onChange:handlePagination,total:totalItems,pageSize:paginationState.itemsPerPage}}
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

export default Agencies;
