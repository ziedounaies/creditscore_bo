import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim.reducer';

export const ClaimDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimEntity = useAppSelector(state => state.claim.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimDetailsHeading">Claim</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{claimEntity.id}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{claimEntity.status}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{claimEntity.title}</dd>
          <dt>
            <span id="message">Message</span>
          </dt>
          <dd>{claimEntity.message}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{claimEntity.createdAt ? <TextFormat value={claimEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Member User</dt>
          <dd>{claimEntity.memberUser ? claimEntity.memberUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim/${claimEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimDetail;
