import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
            <span id="nameEmitter">Name Emitter</span>
          </dt>
          <dd>{claimEntity.nameEmitter}</dd>
          <dt>
            <span id="message">Message</span>
          </dt>
          <dd>{claimEntity.message}</dd>
          <dt>Notification</dt>
          <dd>
            {claimEntity.notifications
              ? claimEntity.notifications.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {claimEntity.notifications && i === claimEntity.notifications.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
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
