import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './banks.reducer';

export const BanksDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const banksEntity = useAppSelector(state => state.banks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="banksDetailsHeading">Banks</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{banksEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{banksEntity.name}</dd>
          <dt>
            <span id="foundedDate">Founded Date</span>
          </dt>
          <dd>{banksEntity.foundedDate ? <TextFormat value={banksEntity.foundedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="branches">Branches</span>
          </dt>
          <dd>{banksEntity.branches}</dd>
          <dt>
            <span id="enabled">Enabled</span>
          </dt>
          <dd>{banksEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{banksEntity.createdAt ? <TextFormat value={banksEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/banks" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banks/${banksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BanksDetail;
