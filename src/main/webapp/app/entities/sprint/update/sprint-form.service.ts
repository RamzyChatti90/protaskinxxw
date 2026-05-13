import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISprint, NewSprint } from '../sprint.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISprint for edit and NewSprintFormGroupInput for create.
 */
type SprintFormGroupInput = ISprint | PartialWithRequiredKeyOf<NewSprint>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISprint | NewSprint> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type SprintFormRawValue = FormValueOf<ISprint>;

type NewSprintFormRawValue = FormValueOf<NewSprint>;

type SprintFormDefaults = Pick<NewSprint, 'id' | 'createdAt'>;

type SprintFormGroupContent = {
  id: FormControl<SprintFormRawValue['id'] | NewSprint['id']>;
  name: FormControl<SprintFormRawValue['name']>;
  description: FormControl<SprintFormRawValue['description']>;
  startDate: FormControl<SprintFormRawValue['startDate']>;
  createdAt: FormControl<SprintFormRawValue['createdAt']>;
  owner: FormControl<SprintFormRawValue['owner']>;
};

export type SprintFormGroup = FormGroup<SprintFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SprintFormService {
  createSprintFormGroup(sprint: SprintFormGroupInput = { id: null }): SprintFormGroup {
    const sprintRawValue = this.convertSprintToSprintRawValue({
      ...this.getFormDefaults(),
      ...sprint,
    });
    return new FormGroup<SprintFormGroupContent>({
      id: new FormControl(
        { value: sprintRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(sprintRawValue.name, {
        validators: [Validators.required],
      }),
      description: new FormControl(sprintRawValue.description),
      startDate: new FormControl(sprintRawValue.startDate),
      createdAt: new FormControl(sprintRawValue.createdAt),
      owner: new FormControl(sprintRawValue.owner),
    });
  }

  getSprint(form: SprintFormGroup): ISprint | NewSprint {
    return this.convertSprintRawValueToSprint(form.getRawValue() as SprintFormRawValue | NewSprintFormRawValue);
  }

  resetForm(form: SprintFormGroup, sprint: SprintFormGroupInput): void {
    const sprintRawValue = this.convertSprintToSprintRawValue({ ...this.getFormDefaults(), ...sprint });
    form.reset(
      {
        ...sprintRawValue,
        id: { value: sprintRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SprintFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
    };
  }

  private convertSprintRawValueToSprint(rawSprint: SprintFormRawValue | NewSprintFormRawValue): ISprint | NewSprint {
    return {
      ...rawSprint,
      createdAt: dayjs(rawSprint.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertSprintToSprintRawValue(
    sprint: ISprint | (Partial<NewSprint> & SprintFormDefaults),
  ): SprintFormRawValue | PartialWithRequiredKeyOf<NewSprintFormRawValue> {
    return {
      ...sprint,
      createdAt: sprint.createdAt ? sprint.createdAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
