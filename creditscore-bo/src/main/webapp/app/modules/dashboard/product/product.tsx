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
import { getEntities, deleteEntity } from './product.reducer';

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

const Product = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('');
  const [loadModal, setLoadModal] = useState(false);

  const productList = useAppSelector(state => state.product.entities);
  const loading = useAppSelector(state => state.product.loading);
  const productEntity = useAppSelector(state => state.product.entity);
  const { id } = useParams<'id'>();
  const [deleteProductId, setDeleteProductId] = useState<string>('');


  const handleMenuClick: any = (e: any, id: string, productData: any) => {
    if (e?.key === "edit") {
      navigate(`/dashboard/product/${id}/edit`, { state: { productData } });
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
    dispatch(deleteEntity(deleteProductId)); // Use the ID from state for deletion
    setTimeout(() => {
      setVisible(false);
      setConfirmLoading(false);
    });
  };
  const confirmDelete = (productId: string) => {
    setModalText(`Êtes-vous certain de vouloir supprimer le Produit ${productId} ?`);
    setVisible(true);
    setDeleteProductId(productId); // Set the ID to delete in state
  };

  const columns: TableColumnsType = [
    { title: 'id', dataIndex: 'id', key: 'id' },
    { title: 'Nom', dataIndex: 'name', key: 'name' },
    { title: 'Numéro série', dataIndex: 'serialNumber', key: 'serialNumber' },
    { title: 'Garantie', dataIndex: 'guarantee', key: 'guarantee' },
    { title: 'Prix', dataIndex: 'price', key: 'price', render: (text) =>text && `${text?.replace(/\B(?=(\d{3})+(?!\d))/g, " ")} TND`, },

    {
      title: 'Action',
      dataIndex: 'id',
      width: 90,
      key: 'x',
      render: (text: string, record: any) => <Dropdown menu={{
        items,
        onClick: e => handleMenuClick(e, text, record),
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
        <Title style={{marginBottom:0}} id="product-heading" data-cy="ProductHeading" level={2}>Produits</Title>


          <Text type="secondary">
            Produits Financiers - Découvrez Nos Offres de Crédit
          </Text>

        </Col>
        <div  style={{marginTop:15}} className="d-flex justify-content-end">
          <Button className="me-2" onClick={handleSyncList}>Actualiser la liste</Button>
          <Button type="primary" onClick={() => navigate('/dashboard/product/new')}>Créer un nouveau produit</Button>
        </div>
      </div>
      <Table
        style={{ borderWidth: 2, borderColor: 'red',marginTop:20 }}
        columns={columns}
        dataSource={productList}
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

export default Product;
