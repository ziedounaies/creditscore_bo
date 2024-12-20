import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
            <span id="nameAgency">Name Agency</span>
          </dt>
          <dd>{agenciesEntity.nameAgency}</dd>
          <dt>
            <span id="datefounded">Datefounded</span>
          </dt>
          <dd>{agenciesEntity.datefounded}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{agenciesEntity.address}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{agenciesEntity.city}</dd>
          <dt>
            <span id="codePostal">Code Postal</span>
          </dt>
          <dd>{agenciesEntity.codePostal}</dd>
          <dt>
            <span id="typeContact">Type Contact</span>
          </dt>
          <dd>{agenciesEntity.typeContact}</dd>
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
