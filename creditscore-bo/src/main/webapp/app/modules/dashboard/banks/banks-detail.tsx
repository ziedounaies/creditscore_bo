import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
            <span id="bankName">Bank Name</span>
          </dt>
          <dd>{banksEntity.bankName}</dd>
          <dt>
            <span id="founded">Founded</span>
          </dt>
          <dd>{banksEntity.founded}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{banksEntity.address}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{banksEntity.city}</dd>
          <dt>
            <span id="codePostal">Code Postal</span>
          </dt>
          <dd>{banksEntity.codePostal}</dd>
          <dt>
            <span id="branches">Branches</span>
          </dt>
          <dd>{banksEntity.branches}</dd>
          <dt>
            <span id="typeContact">Type Contact</span>
          </dt>
          <dd>{banksEntity.typeContact}</dd>
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
