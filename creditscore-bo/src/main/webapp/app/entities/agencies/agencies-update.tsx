import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBanks } from 'app/shared/model/banks.model';
import { getEntities as getBanks } from 'app/entities/banks/banks.reducer';
import { IAgencies } from 'app/shared/model/agencies.model';
import { getEntity, updateEntity, createEntity, reset } from './agencies.reducer';

export const AgenciesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const banks = useAppSelector(state => state.banks.entities);
  const agenciesEntity = useAppSelector(state => state.agencies.entity);
  const loading = useAppSelector(state => state.agencies.loading);
  const updating = useAppSelector(state => state.agencies.updating);
  const updateSuccess = useAppSelector(state => state.agencies.updateSuccess);

  const handleClose = () => {
    navigate('/agencies' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBanks({}));
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
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...agenciesEntity,
      ...values,
      banks: banks.find(it => it.id.toString() === values.banks.toString()),
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
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...agenciesEntity,
          createdAt: convertDateTimeFromServer(agenciesEntity.createdAt),
          banks: agenciesEntity?.banks?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.agencies.home.createOrEditLabel" data-cy="AgenciesCreateUpdateHeading">
            Créer ou éditer un Agencies
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="agencies-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="agencies-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Datefounded" id="agencies-datefounded" name="datefounded" data-cy="datefounded" type="text" />
              <ValidatedField label="Enabled" id="agencies-enabled" name="enabled" data-cy="enabled" check type="checkbox" />
              <ValidatedField
                label="Created At"
                id="agencies-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="agencies-banks" name="banks" data-cy="banks" label="Banks" type="select">
                <option value="" key="0" />
                {banks
                  ? banks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/agencies" replace color="info">
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

export default AgenciesUpdate;
