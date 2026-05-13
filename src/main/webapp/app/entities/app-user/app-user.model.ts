import { IUser } from 'app/entities/user/user.model';

export interface IAppUser {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  avatarUrl?: string | null;
  bio?: string | null;
  phone?: string | null;
  internalUser?: Pick<IUser, 'id'> | null;
}

export type NewAppUser = Omit<IAppUser, 'id'> & { id: null };
