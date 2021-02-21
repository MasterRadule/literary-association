import axios from 'axios'

const BASE_URL = process.env.REACT_APP_BASE_URL

const getGenres = async () => {
    const response = await axios.get(`${BASE_URL}/genres`)
    return response.data
}

const genreService = {
    getGenres
}


export default genreService
