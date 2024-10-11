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

import { getEntities } from './member-user.reducer';

export const MemberUser = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const memberUserList = useAppSelector(state => state.memberUser.entities);
  const loading = useAppSelector(state => state.memberUser.loading);
  const totalItems = useAppSelector(state => state.memberUser.totalItems);

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
      <h2 id="member-user-heading" data-cy="MemberUserHeading">
        MemberUsers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Actualiser la liste
          </Button>
          <Link to="/member-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Créer un nouveau Member User
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {memberUserList && memberUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('userName')}>
                  User Name <FontAwesomeIcon icon={getSortIconByFieldName('userName')} />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  First Name <FontAwesomeIcon icon={getSortIconByFieldName('firstName')} />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  Last Name <FontAwesomeIcon icon={getSortIconByFieldName('lastName')} />
                </th>
                <th className="hand" onClick={sort('businessName')}>
                  Business Name <FontAwesomeIcon icon={getSortIconByFieldName('businessName')} />
                </th>
                <th className="hand" onClick={sort('birthDate')}>
                  Birth Date <FontAwesomeIcon icon={getSortIconByFieldName('birthDate')} />
                </th>
                <th className="hand" onClick={sort('acountType')}>
                  Acount Type <FontAwesomeIcon icon={getSortIconByFieldName('acountType')} />
                </th>
                <th className="hand" onClick={sort('identifierType')}>
                  Identifier Type <FontAwesomeIcon icon={getSortIconByFieldName('identifierType')} />
                </th>
                <th className="hand" onClick={sort('identifierValue')}>
                  Identifier Value <FontAwesomeIcon icon={getSortIconByFieldName('identifierValue')} />
                </th>
                <th className="hand" onClick={sort('employersReported')}>
                  Employers Reported <FontAwesomeIcon icon={getSortIconByFieldName('employersReported')} />
                </th>
                <th className="hand" onClick={sort('income')}>
                  Income <FontAwesomeIcon icon={getSortIconByFieldName('income')} />
                </th>
                <th className="hand" onClick={sort('expenses')}>
                  Expenses <FontAwesomeIcon icon={getSortIconByFieldName('expenses')} />
                </th>
                <th className="hand" onClick={sort('grossProfit')}>
                  Gross Profit <FontAwesomeIcon icon={getSortIconByFieldName('grossProfit')} />
                </th>
                <th className="hand" onClick={sort('netProfitMargin')}>
                  Net Profit Margin <FontAwesomeIcon icon={getSortIconByFieldName('netProfitMargin')} />
                </th>
                <th className="hand" onClick={sort('debtsObligations')}>
                  Debts Obligations <FontAwesomeIcon icon={getSortIconByFieldName('debtsObligations')} />
                </th>
                <th className="hand" onClick={sort('enabled')}>
                  Enabled <FontAwesomeIcon icon={getSortIconByFieldName('enabled')} />
                </th>
                <th className="hand" onClick={sort('role')}>
                  Role <FontAwesomeIcon icon={getSortIconByFieldName('role')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {memberUserList.map((memberUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/member-user/${memberUser.id}`} color="link" size="sm">
                      {memberUser.id}
                    </Button>
                  </td>
                  <td>{memberUser.userName}</td>
                  <td>{memberUser.firstName}</td>
                  <td>{memberUser.lastName}</td>
                  <td>{memberUser.businessName}</td>
                  <td>{memberUser.birthDate ? <TextFormat type="date" value={memberUser.birthDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{memberUser.acountType}</td>
                  <td>{memberUser.identifierType}</td>
                  <td>{memberUser.identifierValue}</td>
                  <td>{memberUser.employersReported}</td>
                  <td>{memberUser.income}</td>
                  <td>{memberUser.expenses}</td>
                  <td>{memberUser.grossProfit}</td>
                  <td>{memberUser.netProfitMargin}</td>
                  <td>{memberUser.debtsObligations}</td>
                  <td>{memberUser.enabled ? 'true' : 'false'}</td>
                  <td>{memberUser.role}</td>
                  <td>{memberUser.createdAt ? <TextFormat type="date" value={memberUser.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/member-user/${memberUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Voir</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/member-user/${memberUser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/member-user/${memberUser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
          !loading && <div className="alert alert-warning">Aucun Member User trouvé</div>
        )}
      </div>
      {totalItems ? (
        <div className={memberUserList && memberUserList.length > 0 ? '' : 'd-none'}>
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

export default MemberUser;
