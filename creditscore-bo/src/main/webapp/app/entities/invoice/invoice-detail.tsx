import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './invoice.reducer';

export const InvoiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="invoiceDetailsHeading">Invoice</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{invoiceEntity.id}</dd>
          <dt>
            <span id="invoiceNumber">Invoice Number</span>
          </dt>
          <dd>{invoiceEntity.invoiceNumber}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{invoiceEntity.amount}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{invoiceEntity.status}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{invoiceEntity.createdAt ? <TextFormat value={invoiceEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Member User</dt>
          <dd>{invoiceEntity.memberUser ? invoiceEntity.memberUser.id : ''}</dd>
          <dt>Credit Rapport</dt>
          <dd>{invoiceEntity.creditRapport ? invoiceEntity.creditRapport.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice/${invoiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InvoiceDetail;
