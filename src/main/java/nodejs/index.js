const functions = require('@google-cloud/functions-framework');
const axios = require('axios');

functions.http('scrap-web', async (req, res) => {
    try {
        const name = req.query.url || 'https://www.google.com/';
        // Make an HTTP request to an external API
        const response = await axios.get(name);

        // Send the response back to the client
        res.status(200).send({
            message: name,
            data: response.data,
        });
    } catch (error) {
        console.error('Error making HTTP request:', error);
        res.status(500).send({
            message: 'Failed to retrieve data',
            error: error.message,
        });
    }
});