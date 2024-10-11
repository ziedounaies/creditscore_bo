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
            <span id="userName">User Name</span>
          </dt>
          <dd>{memberUserEntity.userName}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{memberUserEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{memberUserEntity.lastName}</dd>
          <dt>
            <span id="businessName">Business Name</span>
          </dt>
          <dd>{memberUserEntity.businessName}</dd>
          <dt>
            <span id="birthDate">Birth Date</span>
          </dt>
          <dd>
            {memberUserEntity.birthDate ? <TextFormat value={memberUserEntity.birthDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="acountType">Acount Type</span>
          </dt>
          <dd>{memberUserEntity.acountType}</dd>
          <dt>
            <span id="identifierType">Identifier Type</span>
          </dt>
          <dd>{memberUserEntity.identifierType}</dd>
          <dt>
            <span id="identifierValue">Identifier Value</span>
          </dt>
          <dd>{memberUserEntity.identifierValue}</dd>
          <dt>
            <span id="employersReported">Employers Reported</span>
          </dt>
          <dd>{memberUserEntity.employersReported}</dd>
          <dt>
            <span id="income">Income</span>
          </dt>
          <dd>{memberUserEntity.income}</dd>
          <dt>
            <span id="expenses">Expenses</span>
          </dt>
          <dd>{memberUserEntity.expenses}</dd>
          <dt>
            <span id="grossProfit">Gross Profit</span>
          </dt>
          <dd>{memberUserEntity.grossProfit}</dd>
          <dt>
            <span id="netProfitMargin">Net Profit Margin</span>
          </dt>
          <dd>{memberUserEntity.netProfitMargin}</dd>
          <dt>
            <span id="debtsObligations">Debts Obligations</span>
          </dt>
          <dd>{memberUserEntity.debtsObligations}</dd>
          <dt>
            <span id="enabled">Enabled</span>
          </dt>
          <dd>{memberUserEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="role">Role</span>
          </dt>
          <dd>{memberUserEntity.role}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {memberUserEntity.createdAt ? <TextFormat value={memberUserEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
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
