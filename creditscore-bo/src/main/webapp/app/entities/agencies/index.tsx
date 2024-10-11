import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Agencies from './agencies';
import AgenciesDetail from './agencies-detail';
import AgenciesUpdate from './agencies-update';
import AgenciesDeleteDialog from './agencies-delete-dialog';

const AgenciesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Agencies />} />
    <Route path="new" element={<AgenciesUpdate />} />
    <Route path=":id">
      <Route index element={<AgenciesDetail />} />
      <Route path="edit" element={<AgenciesUpdate />} />
      <Route path="delete" element={<AgenciesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AgenciesRoutes;
