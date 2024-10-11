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
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { getEntities as getInvoices } from 'app/entities/invoice/invoice.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import { getEntity, updateEntity, createEntity, reset } from './payment.reducer';

export const PaymentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const products = useAppSelector(state => state.product.entities);
  const invoices = useAppSelector(state => state.invoice.entities);
  const paymentEntity = useAppSelector(state => state.payment.entity);
  const loading = useAppSelector(state => state.payment.loading);
  const updating = useAppSelector(state => state.payment.updating);
  const updateSuccess = useAppSelector(state => state.payment.updateSuccess);
  const paymentTypeValues = Object.keys(PaymentType);
  const statusTypeValues = Object.keys(StatusType);

  const handleClose = () => {
    navigate('/payment' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberUsers({}));
    dispatch(getProducts({}));
    dispatch(getInvoices({}));
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
    values.checkDate = convertDateTimeToServer(values.checkDate);
    values.dateOfSignature = convertDateTimeToServer(values.dateOfSignature);
    values.expectedPaymentDate = convertDateTimeToServer(values.expectedPaymentDate);
    values.datePaymentMade = convertDateTimeToServer(values.datePaymentMade);
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...paymentEntity,
      ...values,
      products: mapIdList(values.products),
      memberUser: memberUsers.find(it => it.id.toString() === values.memberUser.toString()),
      invoice: invoices.find(it => it.id.toString() === values.invoice.toString()),
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
          checkDate: displayDefaultDateTime(),
          dateOfSignature: displayDefaultDateTime(),
          expectedPaymentDate: displayDefaultDateTime(),
          datePaymentMade: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
        }
      : {
          paymentMethod: 'CHECK',
          status: 'PENDING',
          ...paymentEntity,
          checkDate: convertDateTimeFromServer(paymentEntity.checkDate),
          dateOfSignature: convertDateTimeFromServer(paymentEntity.dateOfSignature),
          expectedPaymentDate: convertDateTimeFromServer(paymentEntity.expectedPaymentDate),
          datePaymentMade: convertDateTimeFromServer(paymentEntity.datePaymentMade),
          createdAt: convertDateTimeFromServer(paymentEntity.createdAt),
          memberUser: paymentEntity?.memberUser?.id,
          products: paymentEntity?.products?.map(e => e.id.toString()),
          invoice: paymentEntity?.invoice?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="creditScoreApp.payment.home.createOrEditLabel" data-cy="PaymentCreateUpdateHeading">
            Créer ou éditer un Payment
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="payment-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Check Number" id="payment-checkNumber" name="checkNumber" data-cy="checkNumber" type="text" />
              <ValidatedField label="Check Issuer" id="payment-checkIssuer" name="checkIssuer" data-cy="checkIssuer" type="text" />
              <ValidatedField label="Account Number" id="payment-accountNumber" name="accountNumber" data-cy="accountNumber" type="text" />
              <ValidatedField
                label="Check Date"
                id="payment-checkDate"
                name="checkDate"
                data-cy="checkDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Recipient" id="payment-recipient" name="recipient" data-cy="recipient" type="text" />
              <ValidatedField
                label="Date Of Signature"
                id="payment-dateOfSignature"
                name="dateOfSignature"
                data-cy="dateOfSignature"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Payment Method" id="payment-paymentMethod" name="paymentMethod" data-cy="paymentMethod" type="select">
                {paymentTypeValues.map(paymentType => (
                  <option value={paymentType} key={paymentType}>
                    {paymentType}
                  </option>
                ))}
              </ValidatedField>

              <ValidatedField label="Amount" id="payment-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField
                label="Expected Payment Date"
                id="payment-expectedPaymentDate"
                name="expectedPaymentDate"
                data-cy="expectedPaymentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Date Payment Made"
                id="payment-datePaymentMade"
                name="datePaymentMade"
                data-cy="datePaymentMade"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Status" id="payment-status" name="status" data-cy="status" type="select">
                {statusTypeValues.map(statusType => (
                  <option value={statusType} key={statusType}>
                    {statusType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Currency" id="payment-currency" name="currency" data-cy="currency" type="text" />
              <ValidatedField
                label="Created At"
                id="payment-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="payment-memberUser" name="memberUser" data-cy="memberUser" label="Member User" type="select">
                <option value="" key="0" />
                {memberUsers
                  ? memberUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Product" id="payment-product" data-cy="product" type="select" multiple name="products">
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="payment-invoice" name="invoice" data-cy="invoice" label="Invoice" type="select">
                <option value="" key="0" />
                {invoices
                  ? invoices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment" replace color="info">
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

export default PaymentUpdate;
