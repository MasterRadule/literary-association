import React, { useState } from 'react'
import { Button, Form as BootstrapForm } from 'react-bootstrap'
import FormField from './FormField'

const Form = ({ form, onSubmit }) => {
    const [state, setState] = useState(createFormState(form.formFields))

    const changeState = ({ target }) => {
        const newState = { ...state }
        if (Array.isArray(newState[target.id])) {
            if (target.value === '') {
                return
            }

            if (newState[target.id].includes(target.value)) {
                newState[target.id] = newState[target.id].filter(v => v !== target.value)
            } else {
                newState[target.id] = [...newState[target.id], target.value]
            }
        } else if (typeof newState[target.id] === 'boolean') {
            newState[target.id] = !newState[target.id]
        } else {
            newState[target.id] = target.value
        }
        setState(newState)
    }

    const isVisible = (formField) => {
        if (formField.properties.dependsOn === null) {
            return true
        }
        return state[formField.properties.dependsOn]
    }

    const submit = (event) => {
        event.preventDefault()
        onSubmit(state)
    }

    return (
        <BootstrapForm onSubmit={submit}>
            {form.formFields.map((formField) =>
                isVisible(formField) &&
                < FormField key={formField.id} formField={formField} onChange={changeState}
                    value={state[formField.id]}/>
            )
            }
            <Button variant="primary" type="submit">Submit</Button>
        </BootstrapForm>
    )
}

const createFormState = (formFields) => {
    const reducer = (accumulator, currentValue) => {
        const field = currentValue.id
        let value = currentValue.defaultValue
        if (value === null) {
            switch (currentValue.properties.type) {
            case 'select':
                if (currentValue.properties.multiple) {
                    value = []
                } else {
                    value = ''
                }
                break
            case 'checkbox':
                value = false
                break
            default:
                value = ''
            }
        }
        const newAccumulator = { ...accumulator }
        newAccumulator[field] = value
        return newAccumulator
    }
    return formFields.reduce(reducer, {})
}

export default Form