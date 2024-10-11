import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import {Button, Row, Col, Form, Input, Card, Divider, Typography, Select} from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';
import {
  BarcodeOutlined, CalendarOutlined,
  CheckCircleOutlined,
  DollarOutlined,
  FileTextOutlined,
  SafetyCertificateOutlined, UserOutlined
} from "@ant-design/icons";
import { getEntities as getInvoices } from 'app/entities/invoice/invoice.reducer';
import {getEntities as getPayments} from "app/entities/payment/payment.reducer";
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from "app/shared/util/date-utils";
const { Title,Text } = Typography;

const ProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const invoices = useAppSelector(state => state.invoice.entities);
  const payments = useAppSelector(state => state.payment.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);

  const [form] = Form.useForm();

  const handleClose = () => {
    navigate('/product' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInvoices({}));
    dispatch(getPayments({}));
  }, []);


  useEffect(() => {
    if (updateSuccess) {
      // Redirect to "/dashboard/bank" after successful update
      navigate('/dashboard/product');
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = async (values) => {
    // Check if the ID is not undefined and convert it to a number if needed
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    // Convert createdAt field to server format
    values.createdAt = convertDateTimeToServer(values.createdAt);

    // Prepare the entity object with updated or new values
    const entity = {
      ...productEntity, // Existing entity data
      ...values, // Updated or new values from the form
      invoice: invoices.find(it => it.id.toString() === values.invoice.toString()), // Assuming invoice is selected correctly
    };

    try {
      // Dispatch the appropriate action based on whether it's a new entity or an update
      if (isNew) {
        await dispatch(createEntity(entity)); // Assuming createEntity is an async action
      } else {
        await dispatch(updateEntity(entity)); // Assuming updateEntity is an async action
      }

      // Redirect to "/dashboard/product" after successful save
      navigate('/dashboard/product');
    } catch (error) {
      console.error('Error saving entity:', error);
      // Handle error saving entity (e.g., show error message)
    }
  };


  const defaultValues = () =>
    isNew
      ? {
        createdAt: displayDefaultDateTime(),
      }
      : {
        ...productEntity,
        createdAt: convertDateTimeFromServer(productEntity.createdAt),
        invoice: productEntity?.invoice?.id,
      };

  useEffect(() => {
    isNew
      ? form.setFieldsValue({
        createdAt: displayDefaultDateTime(),
      })
      : form.setFieldsValue({
        ...productEntity,
        createdAt: convertDateTimeFromServer(productEntity.createdAt),
        invoice: productEntity?.invoice?.id,
      });
  }, [productEntity]);

  const handleChange = (value: string) => {
    console.log(`selected ${value}`);
  };

  const CustomPlaceholder = (
    <span>
    <FileTextOutlined style={{ marginRight: 8 ,color:'black'}} />
    Sélectionnez
  </span>
  );

  return (
    <div>
      <Card style={{ borderRadius: 10, width: '100%' ,height:690}}>
        <Row justify="space-between" style={{ marginBottom: 15 }}>
          <Col>



            <Title level={2} style={{marginBottom:0}}>{isNew ? `Créer un produit`: `Fiche produit`}</Title>


            <Text type="secondary">
              Produits Financiers - Découvrez Nos Offres de Crédit
            </Text>

          </Col>
          <Col>
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Link to="/dashboard/product">
                <Button className="me-2" style={{ borderRadius: 10, backgroundColor: 'white', color: '#333' }}>
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Retour</span>
                </Button>
              </Link>
              <Button type="primary" style={{ borderRadius: 10, backgroundColor: '#FF3029' }} htmlType="submit" form="productForm" id="save-entity" data-cy="entityCreateSaveButton"  disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </div>
          </Col>
        </Row>
        <Divider />
        <Row>
          <Col span={11}>
            <Form layout="vertical"  name="productForm" onFinish={saveEntity}
                  initialValues={defaultValues()}
                  form={form}
              // Ensure correct usage of spread operator here
                  {...{ onFinish: saveEntity, initialValues: defaultValues() }}>

              <Form.Item
                id="product-name"
label="Nom produit"
                name="name"
rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Input prefix={<FileTextOutlined className="site-form-item-icon" />} placeholder="nom produit" />
              </Form.Item>


              <Form.Item
                id="product-serialNumber"
                label="Numéro de série"
                name="serialNumber"
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Input
                  prefix={<BarcodeOutlined   className="site-form-item-icon" />}

                  placeholder="numéro de série "
                />
              </Form.Item>


              <Form.Item
                id="product-guarantee"
                label="Garantie"
                name="guarantee"
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Input
                  prefix={<SafetyCertificateOutlined    className="site-form-item-icon" />}

                  placeholder="garantie"
                />
              </Form.Item>


              <Form.Item
                id="product-price"
                label="Prix"
                name="price"
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Input
                  prefix={<DollarOutlined     className="site-form-item-icon" />}

                  placeholder="prix"
                />
              </Form.Item>





              <Form.Item
                id="product-invoice"
                label="factures"
                name="invoice"
                rules={[{required:true,message: 'Veuillez renseigner ce champ !'}]}
              >
                <Select
                  placeholder={CustomPlaceholder}
                  style={{ width: 600 }}
                  onChange={handleChange}
                >
                  <option value="" key="0" />
                  {invoices
                    ? invoices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.invoiceNumber}
                      </option>
                    ))
                    : null}

                </Select>
              </Form.Item>

             {/* <Title level={5}>Date</Title>
              <Form.Item
                name="date"
                rules={[{ required: true, message: 'Veuillez saisir date !' }]}
              >
                <Input
                  prefix={<CalendarOutlined    className="site-form-item-icon" />}
                  type="datetime-local"

                  placeholder="date"

                />
              </Form.Item>*/}

              {/* Add other fields related to Product */}
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

export default ProductUpdate;
