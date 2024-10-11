import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './member-user.reducer';

export const MemberUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const memberUserEntity = useAppSelector(state => state.memberUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="memberUserDetailsHeading">Member User</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{memberUserEntity.id}</dd>
          <dt>
            <span id="dateBirth">Date Birth</span>
          </dt>
          <dd>
            {memberUserEntity.dateBirth ? <TextFormat value={memberUserEntity.dateBirth} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="document">Document</span>
          </dt>
          <dd>{memberUserEntity.document}</dd>
          <dt>
            <span id="creditScore">Credit Score</span>
          </dt>
          <dd>{memberUserEntity.creditScore}</dd>
          <dt>
            <span id="employersReported">Employers Reported</span>
          </dt>
          <dd>{memberUserEntity.employersReported}</dd>
          <dt>
            <span id="role">Role</span>
          </dt>
          <dd>{memberUserEntity.role}</dd>
          <dt>
            <span id="typeContact">Type Contact</span>
          </dt>
          <dd>{memberUserEntity.typeContact}</dd>
          <dt>Member User</dt>
          <dd>{memberUserEntity.memberUser ? memberUserEntity.memberUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/member-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/member-user/${memberUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MemberUserDetail;
