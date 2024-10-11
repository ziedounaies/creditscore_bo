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
import { IBanks } from 'app/shared/model/banks.model';
import { getEntities as getBanks } from 'app/entities/banks/banks.reducer';
import { IAgencies } from 'app/shared/model/agencies.model';
import { getEntities as getAgencies } from 'app/entities/agencies/agencies.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';

export const AddressUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const banks = useAppSelector(state => state.banks.entities);
  const agencies = useAppSelector(state => state.agencies.entities);
  const addressEntity = useAppSelector(state => state.address.entity);
  const loading = useAppSelector(state => state.address.loading);
  const updating = useAppSelector(state => state.address.updating);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);

  const handleClose = () => {
    navigate('/address' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
    dispatch(getBanks({}));
    dispatch(getAgencies({}));
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
      ...addressEntity,
      ...values,
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
      banks: banks.find(it => it.id.toString() === values.banks.toString()),
      agencies: agencies.find(it => it.id.toString() === values.agencies.toString()),
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
          ...addressEntity,
          createdAt: convertDateTimeFromServer(addressEntity.createdAt),
          memberUser: addressEntity?.memberUser?.id,
          banks: addressEntity?.banks?.id,
          agencies: addressEntity?.agencies?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.address.home.createOrEditLabel" data-cy="AddressCreateUpdateHeading">
            Créer ou éditer un Address
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="address-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Street" id="address-street" name="street" data-cy="street" type="text" />
              <ValidatedField label="City" id="address-city" name="city" data-cy="city" type="text" />
              <ValidatedField label="Zip Code" id="address-zipCode" name="zipCode" data-cy="zipCode" type="text" />
              <ValidatedField
                label="Created At"
                id="address-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="address-memberUser" name="memberUser" data-cy="memberUser" label="Member User" type="select">
                <option value="" key="0" />
                {memberUsers
                  ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="address-banks" name="banks" data-cy="banks" label="Banks" type="select">
                <option value="" key="0" />
                {banks
                  ? banks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="address-agencies" name="agencies" data-cy="agencies" label="Agencies" type="select">
                <option value="" key="0" />
                {agencies
                  ? agencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/address" replace color="info">
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

export default AddressUpdate;