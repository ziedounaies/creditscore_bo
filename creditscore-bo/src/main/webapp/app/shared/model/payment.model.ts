import dayjs from 'dayjs';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { IProduct } from 'app/shared/model/product.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';

export interface IPayment {
  id?: number;
  checkNumber?: string | null;
  checkIssuer?: string | null;
  accountNumber?: string | null;
  checkDate?: dayjs.Dayjs | null;
  recipient?: string | null;
  dateOfSignature?: dayjs.Dayjs | null;
  paymentMethod?: keyof typeof PaymentType | null;
  amount?: string | null;
  expectedPaymentDate?: dayjs.Dayjs | null;
  datePaymentMade?: dayjs.Dayjs | null;
  status?: keyof typeof StatusType | null;
  currency?: string | null;
  createdAt?: dayjs.Dayjs | null;
  memberUser?: IMemberUser | null;
  products?: IProduct[] | null;
  invoice?: IInvoice | null;
}

export const defaultValue: Readonly<IPayment> = {};
