import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBanks } from 'app/shared/model/banks.model';
import { getEntity, updateEntity, createEntity, reset } from './banks.reducer';

export const BanksUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const banksEntity = useAppSelector(state => state.banks.entity);
  const loading = useAppSelector(state => state.banks.loading);
  const updating = useAppSelector(state => state.banks.updating);
  const updateSuccess = useAppSelector(state => state.banks.updateSuccess);

  const handleClose = () => {
    navigate('/banks' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.foundedDate = convertDateTimeToServer(values.foundedDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...banksEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          foundedDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...banksEntity,
          foundedDate: convertDateTimeFromServer(banksEntity.foundedDate),
          createdAt: convertDateTimeFromServer(banksEntity.createdAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.banks.home.createOrEditLabel" data-cy="BanksCreateUpdateHeading">
            Créer ou éditer un Banks
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="banks-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="banks-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label="Founded Date"
                id="banks-foundedDate"
                name="foundedDate"
                data-cy="foundedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Branches" id="banks-branches" name="branches" data-cy="branches" type="text" />
              <ValidatedField label="Enabled" id="banks-enabled" name="enabled" data-cy="enabled" check type="checkbox" />
              <ValidatedField
                label="Created At"
                id="banks-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/banks" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Retour</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BanksUpdate;
