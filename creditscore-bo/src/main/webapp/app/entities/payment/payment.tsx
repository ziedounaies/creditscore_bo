import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './payment.reducer';

export const Payment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const paymentList = useAppSelector(state => state.payment.entities);
  const loading = useAppSelector(state => state.payment.loading);
  const totalItems = useAppSelector(state => state.payment.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="payment-heading" data-cy="PaymentHeading">
        Payments
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Actualiser la liste
          </Button>
          <Link to="/payment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Créer un nouveau Payment
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paymentList && paymentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('checkNumber')}>
                  Check Number <FontAwesomeIcon icon={getSortIconByFieldName('checkNumber')} />
                </th>
                <th className="hand" onClick={sort('checkIssuer')}>
                  Check Issuer <FontAwesomeIcon icon={getSortIconByFieldName('checkIssuer')} />
                </th>
                <th className="hand" onClick={sort('accountNumber')}>
                  Account Number <FontAwesomeIcon icon={getSortIconByFieldName('accountNumber')} />
                </th>
                <th className="hand" onClick={sort('checkDate')}>
                  Check Date <FontAwesomeIcon icon={getSortIconByFieldName('checkDate')} />
                </th>
                <th className="hand" onClick={sort('recipient')}>
                  Recipient <FontAwesomeIcon icon={getSortIconByFieldName('recipient')} />
                </th>
                <th className="hand" onClick={sort('dateOfSignature')}>
                  Date Of Signature <FontAwesomeIcon icon={getSortIconByFieldName('dateOfSignature')} />
                </th>
                <th className="hand" onClick={sort('paymentMethod')}>
                  Payment Method <FontAwesomeIcon icon={getSortIconByFieldName('paymentMethod')} />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  Amount <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                </th>
                <th className="hand" onClick={sort('expectedPaymentDate')}>
                  Expected Payment Date <FontAwesomeIcon icon={getSortIconByFieldName('expectedPaymentDate')} />
                </th>
                <th className="hand" onClick={sort('datePaymentMade')}>
                  Date Payment Made <FontAwesomeIcon icon={getSortIconByFieldName('datePaymentMade')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('currency')}>
                  Currency <FontAwesomeIcon icon={getSortIconByFieldName('currency')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  Member User <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Invoice <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paymentList.map((payment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/payment/${payment.id}`} color="link" size="sm">
                      {payment.id}
                    </Button>
                  </td>
                  <td>{payment.checkNumber}</td>
                  <td>{payment.checkIssuer}</td>
                  <td>{payment.accountNumber}</td>
                  <td>{payment.checkDate ? <TextFormat type="date" value={payment.checkDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{payment.recipient}</td>
                  <td>
                    {payment.dateOfSignature ? <TextFormat type="date" value={payment.dateOfSignature} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{payment.paymentMethod}</td>
                  <td>{payment.amount}</td>
                  <td>
                    {payment.expectedPaymentDate ? (
                      <TextFormat type="date" value={payment.expectedPaymentDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {payment.datePaymentMade ? <TextFormat type="date" value={payment.datePaymentMade} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{payment.status}</td>
                  <td>{payment.currency}</td>
                  <td>{payment.createdAt ? <TextFormat type="date" value={payment.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{payment.memberUser ? <Link to={`/member-user/${payment.memberUser.id}`}>{payment.memberUser.id}</Link> : ''}</td>
                  <td>{payment.invoice ? <Link to={`/invoice/${payment.invoice.id}`}>{payment.invoice.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/payment/${payment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Voir</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/payment/${payment.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/payment/${payment.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Supprimer</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Aucun Payment trouvé</div>
        )}
      </div>
      {totalItems ? (
        <div className={paymentList && paymentList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Payment;
