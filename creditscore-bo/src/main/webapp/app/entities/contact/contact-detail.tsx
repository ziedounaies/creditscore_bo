import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contact.reducer';

export const ContactDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactEntity = useAppSelector(state => state.contact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactDetailsHeading">Contact</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{contactEntity.id}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{contactEntity.type}</dd>
          <dt>
            <span id="value">Value</span>
          </dt>
          <dd>{contactEntity.value}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{contactEntity.createdAt ? <TextFormat value={contactEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Member User</dt>
          <dd>{contactEntity.memberUser ? contactEntity.memberUser.id : ''}</dd>
          <dt>Banks</dt>
          <dd>{contactEntity.banks ? contactEntity.banks.id : ''}</dd>
          <dt>Agencies</dt>
          <dd>{contactEntity.agencies ? contactEntity.agencies.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactDetail;
