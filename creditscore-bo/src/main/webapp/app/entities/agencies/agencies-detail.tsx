import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './agencies.reducer';

export const AgenciesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const agenciesEntity = useAppSelector(state => state.agencies.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="agenciesDetailsHeading">Agencies</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{agenciesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{agenciesEntity.name}</dd>
          <dt>
            <span id="datefounded">Datefounded</span>
          </dt>
          <dd>{agenciesEntity.datefounded}</dd>
          <dt>
            <span id="enabled">Enabled</span>
          </dt>
          <dd>{agenciesEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{agenciesEntity.createdAt ? <TextFormat value={agenciesEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Banks</dt>
          <dd>{agenciesEntity.banks ? agenciesEntity.banks.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/agencies" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/agencies/${agenciesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgenciesDetail;
