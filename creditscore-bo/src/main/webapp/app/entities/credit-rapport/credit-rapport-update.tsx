import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMemberUser } from 'app/shared/model/member-user.model';
import { getEntities as getMemberUsers } from 'app/entities/member-user/member-user.reducer';
import { ICreditRapport } from 'app/shared/model/credit-rapport.model';
import { getEntity, updateEntity, createEntity, reset } from './credit-rapport.reducer';

export const CreditRapportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const creditRapportEntity = useAppSelector(state => state.creditRapport.entity);
  const loading = useAppSelector(state => state.creditRapport.loading);
  const updating = useAppSelector(state => state.creditRapport.updating);
  const updateSuccess = useAppSelector(state => state.creditRapport.updateSuccess);

  const handleClose = () => {
    navigate('/credit-rapport' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
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
      ...creditRapportEntity,
      ...values,
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
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
          ...creditRapportEntity,
          createdAt: convertDateTimeFromServer(creditRapportEntity.createdAt),
          memberUser: creditRapportEntity?.memberUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.creditRapport.home.createOrEditLabel" data-cy="CreditRapportCreateUpdateHeading">
            Créer ou éditer un Credit Rapport
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="credit-rapport-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Credit Score" id="credit-rapport-creditScore" name="creditScore" data-cy="creditScore" type="text" />
              <ValidatedField label="Account Age" id="credit-rapport-accountAge" name="accountAge" data-cy="accountAge" type="text" />
              <ValidatedField label="Credit Limit" id="credit-rapport-creditLimit" name="creditLimit" data-cy="creditLimit" type="text" />
              <ValidatedField
                label="Inquiries And Requests"
                id="credit-rapport-inquiriesAndRequests"
                name="inquiriesAndRequests"
                data-cy="inquiriesAndRequests"
                type="text"
              />
              <ValidatedField
                label="Created At"
                id="credit-rapport-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="credit-rapport-memberUser" name="memberUser" data-cy="memberUser" label="Member User" type="select">
                <option value="" key="0" />
                {memberUsers
                  ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/credit-rapport" replace color="info">
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

export default CreditRapportUpdate;
