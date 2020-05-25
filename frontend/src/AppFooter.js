import React from 'react';
import {
    Container,
    Segment,
    List,
    Divider,
  } from 'semantic-ui-react'
  
class AppFooter extends React.Component {
    render() {
        return (
            <div id='footer'>
                <Segment vertical style={{ margin: '5em 0em 0em' }}>
                    <Container textAlign='center'>
                        <Divider inverted section />
                        <List horizontal divided link size='small'>
                            <List.Item as='a' href='#'>
                            Site Map
                            </List.Item>
                            <List.Item as='a' href='#'>
                            Contact Us
                            </List.Item>
                            <List.Item as='a' href='#'>
                            Terms and Conditions
                            </List.Item>
                            <List.Item as='a' href='#'>
                            Privacy Policy
                            </List.Item>
                        </List>
                    </Container>
                </Segment>
            </div>
        );
    }
}

export default AppFooter;