import React, { useEffect, useState } from 'react'
import { Button, ButtonGroup, Form, ToggleButton } from 'react-bootstrap'
import genreService from '../services/genreService'
import { useDispatch, useSelector } from 'react-redux'
import { searchBooks } from '../reducers/bookReducer'

const SearchForm = () => {
    const [firstLoad, setFirstLoad] = useState(true)
    const [genres, setGenres] = useState([])
    const [fields, setFields] = useState({
        title: '',
        writers: '',
        genre: '',
        text: ''
    })
    const [operations, setOperations] = useState({
        title: 'AND',
        writers: 'AND',
        genre: 'AND',
        text: 'AND'
    })

    const setOperation = (name, value) => {
        const changedOperations = {
            ...operations
        }
        changedOperations[name] = value
        setOperations(changedOperations)
    }

    const pageNumber = useSelector(state => state.pagination.page)

    useEffect(() => {
        if (!firstLoad) {
            search()
        }
        else {
            genreService.getGenres().then(result => setGenres(result))
        }
    }, [pageNumber])

    const dispatch = useDispatch()

    const search = () => {
        const params = []
        for (const f in fields) {
            if (fields[f]) {
                params.push({
                    field: f,
                    searchValue: fields[f].replace('"', ''),
                    phrazeQuery: fields[f].includes('"'),
                    andOperation: operations[f] === 'AND'
                })
            }
        }

        const query = {
            pageNumber,
            params
        }

        dispatch(searchBooks(query))
        setFirstLoad(false)
    }

    const onSubmit = (event) => {
        event.preventDefault()
        search()
    }

    return (
        <Form inline className="justify-content-between" onSubmit={onSubmit}>
            <ButtonGroup toggle>
                <ToggleButton
                    size="sm"
                    key="AND"
                    value="AND"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['title'] === 'AND'}
                    onChange={({ currentTarget }) => setOperation('title', currentTarget.value)}
                >AND</ToggleButton>
                <ToggleButton
                    size="sm"
                    key="OR"
                    value="OR"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['title'] === 'OR'}
                    onChange={({ currentTarget }) => setOperation('title', currentTarget.value)}
                >OR</ToggleButton>
            </ButtonGroup>
            <Form.Control
                as="input"
                pattern='^[\p{L}]+([\s]{1,1}[\p{L}]+)*$|^"[\p{L}]+([\s]{1,1}[\p{L}]+)*"$'
                placeholder="title"
                value={fields['title']}
                onChange={({ target }) => setFields({ ...fields, title: target.value })}
            />

            <ButtonGroup toggle>
                <ToggleButton
                    size="sm"
                    key="AND"
                    value="AND"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['writers'] === 'AND'}
                    onChange={({ currentTarget }) => setOperation('writers', currentTarget.value)}
                >AND</ToggleButton>
                <ToggleButton
                    size="sm"
                    key="OR"
                    value="OR"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['writers'] === 'OR'}
                    onChange={({ currentTarget }) => setOperation('writers', currentTarget.value)}
                >OR</ToggleButton>
            </ButtonGroup>
            <Form.Control
                as="input"
                pattern='^[\p{L}]+([\s]{1,1}[\p{L}]+)*$|^"[\p{L}]+([\s]{1,1}[\p{L}]+)*"$'
                placeholder="writers"
                value={fields['writers']}
                onChange={({ target }) => setFields({ ...fields, writers: target.value })}
            />

            <ButtonGroup toggle>
                <ToggleButton
                    size="sm"
                    key="AND"
                    value="AND"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['text'] === 'AND'}
                    onChange={({ currentTarget }) => setOperation('text', currentTarget.value)}
                >AND</ToggleButton>
                <ToggleButton
                    size="sm"
                    key="OR"
                    value="OR"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['text'] === 'OR'}
                    onChange={({ currentTarget }) => setOperation('text', currentTarget.value)}
                >OR</ToggleButton>
            </ButtonGroup>
            <Form.Control
                as="input"
                pattern='^[\p{L}]+([\s]{1,1}[\p{L}]+)*$|^"[\p{L}]+([\s]{1,1}[\p{L}]+)*"$'
                placeholder="text"
                value={fields['text']}
                onChange={({ target }) => setFields({ ...fields, text: target.value })}
            />

            <ButtonGroup toggle>
                <ToggleButton
                    size="sm"
                    key="AND"
                    value="AND"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['genre'] === 'AND'}
                    onChange={({ currentTarget }) => setOperation('genre', currentTarget.value)}
                >AND</ToggleButton>
                <ToggleButton
                    size="sm"
                    key="OR"
                    value="OR"
                    type="radio"
                    variant="secondary"
                    name="radio"
                    checked={operations['genre'] === 'OR'}
                    onChange={({ currentTarget }) => setOperation('genre', currentTarget.value)}
                >OR</ToggleButton>
            </ButtonGroup>
            <Form.Control
                as="select"
                value={fields['genre']}
                onChange={({ target }) => setFields({ ...fields, genre: target.value })}
            >
                <option value="">-</option>
                {
                    genres.map(g => (
                        <option value={g} key={g}>{g}</option>
                    ))
                }
            </Form.Control>
            <Button type="submit" variant="secondary">Search</Button>
        </Form>
    )
}

export default SearchForm
