import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Table } from 'react-bootstrap'
import { getBooks, clearSearch, setMyBooks } from '../reducers/bookReducer'
import BookListItem from './BookListItem'
import Pagination from './Pagination'
import SearchForm from './SearchForm'

const BookList = ({ myBooks }) => {
    const dispatch = useDispatch()

    useEffect(() => {
        if (myBooks) {
            dispatch(getBooks(myBooks))
        } else {
            dispatch(setMyBooks(false))
            dispatch(clearSearch())
        }
    }, [])

    const books = useSelector(state => state.books.list)

    return (
        <div>
            <h2>Books</h2>
            {!myBooks ? <SearchForm/> : null}
            <br/>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        {myBooks ?
                            <>
                                <th>ISBN</th>
                                <th>Title</th>
                                <th>Publisher</th>
                                <th>Year</th>
                                <th/>
                            </> :
                            <>
                                <th>Title</th>
                                <th>Basic info</th>
                                <th>Highlight</th>
                                <th/>
                                <th/>
                            </>
                        }
                    </tr>
                </thead>
                <tbody>
                    {myBooks ?
                        books.map(book =>
                            <BookListItem key={book.id} id={book.id} ISBN={book.isbn} publisher={book.publisher} title={book.title}
                                          year={book.year} myBooks={true}/>) :
                        books.map(book =>
                            <BookListItem key={book.id} id={book.id} basicInfo={book.basicInfo} text={book.text}
                                          title={book.title} openAccess={book.openAccess} myBooks={false}/>)
                    }
                </tbody>
            </Table>
            {!myBooks ? <Pagination/> : null}
        </div>
    )
}

export default BookList
