import dayjs from 'dayjs/esm';
import { IAppUser } from 'app/entities/app-user/app-user.model';

export interface ITeam {
  id: number;
  name?: string | null;
  description?: string | null;
  startDate?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs | null;
  owner?: Pick<IAppUser, 'id'> | null;
}

export type NewTeam = Omit<ITeam, 'id'> & { id: null };
