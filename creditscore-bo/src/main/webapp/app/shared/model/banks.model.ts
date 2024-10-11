import dayjs from 'dayjs';
import { IContact } from 'app/shared/model/contact.model';
import { IAddress } from 'app/shared/model/address.model';
import { IAgencies } from 'app/shared/model/agencies.model';

export interface IBanks {
  id?: number;
  name?: string | null;
  foundedDate?: dayjs.Dayjs | null;
  branches?: string | null;
  enabled?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  contacts?: IContact[] | null;
  addresses?: IAddress[] | null;
  agencies?: IAgencies[] | null;
}

export const defaultValue: Readonly<IBanks> = {
  enabled: false,
};
