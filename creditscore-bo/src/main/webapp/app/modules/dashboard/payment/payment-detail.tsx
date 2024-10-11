import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payment.reducer';

export const PaymentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const paymentEntity = useAppSelector(state => state.payment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentDetailsHeading">Payment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{paymentEntity.id}</dd>
          <dt>
            <span id="checkNumber">Check Number</span>
          </dt>
          <dd>{paymentEntity.checkNumber}</dd>
          <dt>
            <span id="checkIssuer">Check Issuer</span>
          </dt>
          <dd>{paymentEntity.checkIssuer}</dd>
          <dt>
            <span id="accountNumber">Account Number</span>
          </dt>
          <dd>{paymentEntity.accountNumber}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{paymentEntity.amount}</dd>
          <dt>
            <span id="checkDate">Check Date</span>
          </dt>
          <dd>{paymentEntity.checkDate ? <TextFormat value={paymentEntity.checkDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="recipient">Recipient</span>
          </dt>
          <dd>{paymentEntity.recipient}</dd>
          <dt>
            <span id="dateOfSignature">Date Of Signature</span>
          </dt>
          <dd>
            {paymentEntity.dateOfSignature ? (
              <TextFormat value={paymentEntity.dateOfSignature} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Member User</dt>
          <dd>{paymentEntity.memberUser ? paymentEntity.memberUser.id : ''}</dd>
          <dt>Credit Rapport</dt>
          <dd>{paymentEntity.creditRapport ? paymentEntity.creditRapport.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentDetail;
