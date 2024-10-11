import dayjs from 'dayjs';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { IInvoice } from 'app/shared/model/invoice.model';

export interface ICreditRapport {
  id?: number;
  creditScore?: string | null;
  accountAge?: string | null;
  creditLimit?: string | null;
  inquiriesAndRequests?: string | null;
  createdAt?: dayjs.Dayjs | null;
  memberUser?: IMemberUser | null;
  invoices?: IInvoice[] | null;
}

export const defaultValue: Readonly<ICreditRapport> = {};
