import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './credit-rapport.reducer';

export const CreditRapportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const creditRapportEntity = useAppSelector(state => state.creditRapport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="creditRapportDetailsHeading">Credit Rapport</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{creditRapportEntity.id}</dd>
          <dt>
            <span id="creditScore">Credit Score</span>
          </dt>
          <dd>{creditRapportEntity.creditScore}</dd>
          <dt>
            <span id="accountAge">Account Age</span>
          </dt>
          <dd>{creditRapportEntity.accountAge}</dd>
          <dt>
            <span id="creditLimit">Credit Limit</span>
          </dt>
          <dd>{creditRapportEntity.creditLimit}</dd>
          <dt>
            <span id="inquiriesAndRequests">Inquiries And Requests</span>
          </dt>
          <dd>{creditRapportEntity.inquiriesAndRequests}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {creditRapportEntity.createdAt ? (
              <TextFormat value={creditRapportEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Member User</dt>
          <dd>{creditRapportEntity.memberUser ? creditRapportEntity.memberUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/credit-rapport" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/credit-rapport/${creditRapportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CreditRapportDetail;
