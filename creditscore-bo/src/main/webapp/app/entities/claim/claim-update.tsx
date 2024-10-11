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
import { IClaim } from 'app/shared/model/claim.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import { getEntity, updateEntity, createEntity, reset } from './claim.reducer';

export const ClaimUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const claimEntity = useAppSelector(state => state.claim.entity);
  const loading = useAppSelector(state => state.claim.loading);
  const updating = useAppSelector(state => state.claim.updating);
  const updateSuccess = useAppSelector(state => state.claim.updateSuccess);
  const statusTypeValues = Object.keys(StatusType);

  const handleClose = () => {
    navigate('/claim' + location.search);
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
      ...claimEntity,
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
          status: 'PENDING',
          ...claimEntity,
          createdAt: convertDateTimeFromServer(claimEntity.createdAt),
          memberUser: claimEntity?.memberUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.claim.home.createOrEditLabel" data-cy="ClaimCreateUpdateHeading">
            Créer ou éditer un Claim
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="claim-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Status" id="claim-status" name="status" data-cy="status" type="select">
                {statusTypeValues.map(statusType => (
                  <option value={statusType} key={statusType}>
                    {statusType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Title" id="claim-title" name="title" data-cy="title" type="text" />
              <ValidatedField label="Message" id="claim-message" name="message" data-cy="message" type="text" />
              <ValidatedField
                label="Created At"
                id="claim-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="claim-memberUser" name="memberUser" data-cy="memberUser" label="Member User" type="select">
                <option value="" key="0" />
                {memberUsers
                  ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim" replace color="info">
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

export default ClaimUpdate;
