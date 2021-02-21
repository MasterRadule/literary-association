import React, { useState } from 'react'
import { Button } from 'react-bootstrap'
import { useHistory } from 'react-router-dom'
import bookService from '../services/bookService'
import PaymentModal from './PaymentModal'
import ReactHtmlParser from 'react-html-parser'
import { setBook } from '../reducers/bookReducer'
import { useDispatch } from 'react-redux'

const BookListItem = ({ id, ISBN, title, publisher, year, basicInfo, text, openAccess, myBooks }) => {
    const dispatch = useDispatch()
    const history = useHistory()
    const [modalShown, setModalShown] = useState(false)

    const seeBookDetails = () => history.push(`/dashboard/books/${id}`)
    const buttonName = openAccess ? 'Download' : 'Purchase'
    const toggleModal = () => {
        dispatch(setBook(id))
        setModalShown(!modalShown)
    }
    const onClick = () => openAccess ? bookService.downloadBook(title, null) : toggleModal()

    return (
        <>
            <PaymentModal show={modalShown} toggleModal={toggleModal}/>
            <tr>
                {myBooks ?
                    <>
                        <td>{ISBN}</td>
                        <td>{title}</td>
                        <td>{publisher}</td>
                        <td>{year}</td>
                        <td>
                            <Button onClick={seeBookDetails}>Details</Button>
                        </td>
                    </> :
                    <>
                        <td>{title}</td>
                        <td>{basicInfo}</td>
                        <td>{ReactHtmlParser(text)}</td>
                        <td>
                            <Button onClick={onClick}>{buttonName}</Button>
                        </td>
                        <td>
                            <Button onClick={seeBookDetails}>Details</Button>
                        </td>
                    </>
                }
            </tr>
        </>
    )
}

export default BookListItem
