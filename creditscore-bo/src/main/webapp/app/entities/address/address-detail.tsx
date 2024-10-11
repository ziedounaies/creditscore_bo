import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">Address</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="street">Street</span>
          </dt>
          <dd>{addressEntity.street}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="zipCode">Zip Code</span>
          </dt>
          <dd>{addressEntity.zipCode}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{addressEntity.createdAt ? <TextFormat value={addressEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Member User</dt>
          <dd>{addressEntity.memberUser ? addressEntity.memberUser.id : ''}</dd>
          <dt>Banks</dt>
          <dd>{addressEntity.banks ? addressEntity.banks.id : ''}</dd>
          <dt>Agencies</dt>
          <dd>{addressEntity.agencies ? addressEntity.agencies.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
