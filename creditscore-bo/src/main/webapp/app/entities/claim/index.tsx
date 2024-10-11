import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Claim from './claim';
import ClaimDetail from './claim-detail';
import ClaimUpdate from './claim-update';
import ClaimDeleteDialog from './claim-delete-dialog';

const ClaimRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Claim />} />
    <Route path="new" element={<ClaimUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimDetail />} />
      <Route path="edit" element={<ClaimUpdate />} />
      <Route path="delete" element={<ClaimDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimRoutes;
