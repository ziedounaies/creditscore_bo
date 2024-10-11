import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Banks from './banks';
import BanksDetail from './banks-detail';
import BanksUpdate from './banks-update';
import BanksDeleteDialog from './banks-delete-dialog';

const BanksRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Banks />} />
    <Route path="new" element={<BanksUpdate />} />
    <Route path=":id">
      <Route index element={<BanksDetail />} />
      <Route path="edit" element={<BanksUpdate />} />
      <Route path="delete" element={<BanksDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BanksRoutes;
