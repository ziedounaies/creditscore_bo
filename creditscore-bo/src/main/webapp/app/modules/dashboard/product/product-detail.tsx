import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">Product</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="nameProduct">Name Product</span>
          </dt>
          <dd>{productEntity.nameProduct}</dd>
          <dt>
            <span id="serialNumber">Serial Number</span>
          </dt>
          <dd>{productEntity.serialNumber}</dd>
          <dt>
            <span id="guarantee">Guarantee</span>
          </dt>
          <dd>{productEntity.guarantee}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{productEntity.date ? <TextFormat value={productEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Payment</dt>
          <dd>
            {productEntity.payments
              ? productEntity.payments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {productEntity.payments && i === productEntity.payments.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Member User</dt>
          <dd>{productEntity.memberUser ? productEntity.memberUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
