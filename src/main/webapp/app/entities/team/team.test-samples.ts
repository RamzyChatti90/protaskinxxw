import dayjs from 'dayjs/esm';

import { ITeam, NewTeam } from './team.model';

export const sampleWithRequiredData: ITeam = {
  id: 31463,
  name: 'magnifique devant',
};

export const sampleWithPartialData: ITeam = {
  id: 26408,
  name: 'splendide auparavant',
  description: '../fake-data/blob/hipster.txt',
  startDate: dayjs('2026-05-12'),
};

export const sampleWithFullData: ITeam = {
  id: 19481,
  name: 'proche turquoise vétuste',
  description: '../fake-data/blob/hipster.txt',
  startDate: dayjs('2026-05-12'),
  createdAt: dayjs('2026-05-13T03:51'),
};

export const sampleWithNewData: NewTeam = {
  name: 'vouloir incalculable émouvoir',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
